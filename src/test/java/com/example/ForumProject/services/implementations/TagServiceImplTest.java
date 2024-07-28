package com.example.ForumProject.services.implementations;

import com.example.ForumProject.Helpers;
import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.exceptions.EntityDuplicateException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.Tag;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.repositories.contracts.PostRepository;
import com.example.ForumProject.repositories.contracts.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private PostRepository postRepository;

    @Test
    void findByName_TagExists() {
        String tagName = "Tech";
        Tag tag = new Tag();
        tag.setName(tagName);
        when(tagRepository.findByName(tagName)).thenReturn(Optional.of(tag));

        Optional<Tag> result = tagService.findByName(tagName);

        assertTrue(result.isPresent(), "Tag should be present");
        assertEquals(tagName, result.get().getName(), "Tag name should match");
    }

    @Test
    void findByName_TagDoesNotExist() {
        String tagName = "NonExistent";
        when(tagRepository.findByName(tagName)).thenReturn(Optional.empty());

        Optional<Tag> result = tagService.findByName(tagName);

        assertTrue(result.isEmpty(), "Tag should not be present");
    }

    @Test
    void deleteTag() {
    }

    @Test
    void findTagsByPostId_ReturnsTags() {
        int postId = 1;
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        when(tagRepository.findTagsByPostId(postId)).thenReturn(List.of(tag1, tag2));

        List<Tag> tags = tagService.findTagsByPostId(postId);

        assertNotNull(tags);
        assertEquals(2, tags.size());
        assertTrue(tags.contains(tag1));
        assertTrue(tags.contains(tag2));
    }

    @Test
    void findAllTags_Returns_AllTags() {
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        when(tagRepository.findAllTags()).thenReturn(List.of(tag1, tag2));

        List<Tag> tags = tagService.findAllTags();

        assertNotNull(tags);
        assertEquals(2, tags.size());
        assertTrue(tags.contains(tag1));
        assertTrue(tags.contains(tag2));
    }

    @Test
    void findById_Returns_Tag_WhenExists() {
        int tagId = 1;
        Tag tag = new Tag();
        when(tagRepository.findTagById(tagId)).thenReturn(tag);

        Tag result = tagService.findById(tagId);
        assertNotNull(result);
        assertEquals(tag, result);
    }
    @Test
    void findById_TagNotFound_AndThrow() {
        int tagId = 1;
        when(tagRepository.findTagById(tagId)).thenThrow(new EntityNotFoundException("Tag", tagId));

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            tagService.findById(tagId);
        });

        assertEquals("Tag with id 1 not found.", thrown.getMessage());
    }

    @Test
    void updateTag_Success() {
        User adminUser = Helpers.createMockAdmin();
        Tag oldTag = new Tag();
        oldTag.setId(1);

        Tag newTag = new Tag();
        newTag.setId(2);

        when(tagRepository.createOrUpdateTag(newTag)).thenReturn(newTag);
        doNothing().when(tagRepository).deleteTag(oldTag);

        Tag result = tagService.updateTag(oldTag, adminUser, newTag);

        assertNotNull(result);
        assertEquals(newTag, result);
        verify(tagRepository, times(1)).createOrUpdateTag(newTag);
        verify(tagRepository, times(1)).deleteTag(oldTag);
    }

    @Test
    void updateTag_UnauthorizedUser() {
      User normalUser = Helpers.createMockUser();

        Tag oldTag = new Tag();
        oldTag.setId(1);

        Tag newTag = new Tag();
        newTag.setId(2);

        assertThrows(AuthorizationException.class, () -> {
            tagService.updateTag(oldTag, normalUser, newTag);
        });
    }


    @Test
    void createTag_TagDoesNotExist_AndCreatesIt() {
        Tag tag = new Tag();
        tag.setName("NewTag");

        when(tagRepository.findByName(tag.getName())).thenReturn(Optional.empty());
        when(tagRepository.createTag(tag)).thenReturn(tag);

        Tag result = tagService.createTag(tag, new User());

        assertNotNull(result);
        assertEquals(tag.getName(), result.getName());
        verify(tagRepository, times(1)).createTag(tag);
    }

    @Test
    void createTag_TagAlreadyExistsAndThrows() {
        Tag tag = new Tag();
        tag.setName("ExistingTag");

        when(tagRepository.findByName(tag.getName())).thenReturn(Optional.of(tag));

        assertThrows(EntityDuplicateException.class, () -> {
            tagService.createTag(tag, new User());
        });
    }
    @Test
    void deleteTag_Success() {
        User adminUser = Helpers.createMockAdmin();  // Ensure admin role is correctly set

        Tag tag = new Tag();
        tag.setId(1);

        Post post = new Post();
        post.setTags(new HashSet<>());
        post.getTags().add(tag);

        when(postRepository.getPostsByTagId(tag.getId())).thenReturn(List.of(post));
        doNothing().when(tagRepository).deleteTag(tag);

        tagService.deleteTag(tag, adminUser);

        verify(postRepository, times(1)).updatePost(post);
        verify(tagRepository, times(1)).deleteTag(tag);

        // Verify that the tag was removed from the post
        assertFalse(post.getTags().contains(tag), "Tag should be removed from post");
    }
    @Test
    void deleteTag_UnauthorizedUser() {
        User normalUser = Helpers.createMockUser();

        Tag tag = new Tag();
        tag.setId(1);

        assertThrows(AuthorizationException.class, () -> {
            tagService.deleteTag(tag, normalUser);
        });
    }

}