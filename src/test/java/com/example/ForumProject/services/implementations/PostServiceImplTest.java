package com.example.ForumProject.services.implementations;

import com.example.ForumProject.Helpers;
import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.exceptions.EntityDuplicateException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.models.persistentClasses.*;
import com.example.ForumProject.repositories.contracts.PostRepository;
import com.example.ForumProject.repositories.contracts.TagRepository;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.services.contracts.UserService;
import com.example.ForumProject.utility.ValidatorHelpers;
import com.sun.jdi.request.DuplicateRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {


    @Mock
    PostRepository postRepository;

    @Mock
    TagRepository tagRepository;

    @Mock
    UserService userService;

    @InjectMocks
    PostServiceImpl postService;


    @Test
    void getPosts_should_filter_posts_when_parametersAreCorrect() {
        User adminUser = Helpers.createMockAdmin();
        FilterOptionsPosts filterOptionsPosts = Helpers.createMockFilterOptions();
        List<Post> posts = new ArrayList<>();

        posts.add(Helpers.createMockPost());


        Mockito.when(postRepository.getPosts(filterOptionsPosts))
               .thenReturn(posts);

        postService.getPosts(adminUser, filterOptionsPosts);

        Assertions.assertNotNull(postRepository.getPosts(filterOptionsPosts));
        Assertions.assertEquals(postRepository.getPosts(filterOptionsPosts)
                                              .size(), 1);

    }

    @Test
    void getPostById_should_return_entity_when_parametersAreCorrect() {

        Post mockPost = Helpers.createMockPost();
        Mockito.when(postRepository.getPostById(1))
               .thenReturn(mockPost);

        Post result = postService.getPostById(1);

        Assertions.assertEquals(result.getId(), 1);
        Assertions.assertEquals(postRepository.getPostById(1)
                                              .getTitle(), mockPost.getTitle());

    }

    @Test
    void getPostById_should_throw_when_idDoesntExist() {

        Post mockPost = Helpers.createMockPost();
        Mockito.when(postRepository.getPostById(2))
               .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> postService.getPostById(2));

    }


    @Test
    void get10MostCommentedPosts() {

        postService.get10MostCommentedPosts();

        Mockito.verify(postRepository, Mockito.times(1))
               .get10MostCommentedPosts();

    }

    @Test
    void get10MostRecentlyAddedPosts() {

        postService.get10MostRecentlyAddedPosts();

        Mockito.verify(postRepository, Mockito.times(1))
               .get10MostRecentlyAddedPosts();
    }


    @Test
    void createPost_should_create_post_whenParametersAreCorrect() {


        Post mockPost = Helpers.createMockPost();
        User mockUser = Helpers.createMockUser();

        postService.createPost(mockPost, mockUser);

        Mockito.verify(postRepository, Mockito.times(1))
               .createPost(mockPost);

    }


    @Test
    void createPost_should_throw_whenUserAlreadyHasSuchTitle() {


        Post mockPost = Helpers.createMockPost();
        User mockUser = Helpers.createMockUser();


        Mockito.when(postRepository.createPost(mockPost))
               .thenThrow(EntityDuplicateException.class);

        Assertions.assertThrows(EntityDuplicateException.class, () -> postService.createPost(mockPost, mockUser));

    }

    @Test
    void updatePost_should_return_updatedPost() {
        Post post = Helpers.createMockPost();
        Mockito.when(postRepository.updatePost(post))
               .thenReturn(post);

        Post result = postService.updatePost(post);

        Assertions.assertEquals(post, result);
        Mockito.verify(postRepository)
               .updatePost(post);
    }


    @Test
    void updatePost_should_update_existingPost_whenUserIsAdmin() {
        Post post = Helpers.createMockPost();
        User adminUser = Helpers.createMockAdmin();
        Post existingPost = Helpers.createMockPost();

        Mockito.when(postRepository.updatePost(existingPost))
               .thenReturn(existingPost);

        Post result = postService.updatePost(post, adminUser, existingPost);

        Assertions.assertEquals(post.getTitle(), result.getTitle());
        Assertions.assertEquals(post.getContent(), result.getContent());
        Mockito.verify(postRepository)
               .checkIfPostWithTitleExistsForUser(post, adminUser);
        Mockito.verify(postRepository)
               .updatePost(existingPost);
    }

    @Test
    void updatePost_should_throw_whenUserIsNotAdminAndNotAuthor() {
        // Create a mock post with a specific author
        Post existingPost = Helpers.createMockPost();
        User author = Helpers.createMockUser();
        author.setId(1);
        existingPost.setUser(author);

        // Create a user who is not the author and not an admin
        User notAuthorUser = Helpers.createMockUser();
        notAuthorUser.setId(2);

        // Create a new post object to update with
        Post updatedPost = Helpers.createMockPost();

        // Mock the ValidatorHelpers.roleAuthenticationValidator method
        MockedStatic<ValidatorHelpers> mockedValidatorHelpers = Mockito.mockStatic(ValidatorHelpers.class);
        mockedValidatorHelpers.when(() -> ValidatorHelpers.roleAuthenticationValidator(
                Mockito.eq(notAuthorUser),
                Mockito.any(UserRole.class),
                Mockito.eq(existingPost),
                Mockito.anyString()
        )).thenThrow(new AuthorizationException("User is not authorized to update this post"));

        // Assert that the method throws AuthorizationException
        Assertions.assertThrows(AuthorizationException.class,
                () -> postService.updatePost(updatedPost, notAuthorUser, existingPost));

        // Verify that the validator was called
        mockedValidatorHelpers.verify(() -> ValidatorHelpers.roleAuthenticationValidator(
                Mockito.eq(notAuthorUser),
                Mockito.any(UserRole.class),
                Mockito.eq(existingPost),
                Mockito.anyString()
        ));

        mockedValidatorHelpers.close();
    }

    @Test
    void deletePost_should_delete_post_whenUserIsAdmin() {
        int postId = 1;
        User adminUser = Helpers.createMockAdmin();
        Post post = Helpers.createMockPost();

        Mockito.when(postService.getPostById(postId))
               .thenReturn(post);

        postService.deletePost(postId, adminUser);

        Mockito.verify(userService)
               .updateUser(adminUser);
        Mockito.verify(postRepository)
               .deletePost(postId);
    }

    @Test
    void getPostsByUser_should_return_userPosts() {
        int userId = 1;
        List<Post> expectedPosts = Arrays.asList(Helpers.createMockPost(), Helpers.createMockPost());
        Mockito.when(postRepository.getPostsByUser(userId))
               .thenReturn(expectedPosts);

        List<Post> result = postService.getPostsByUser(userId);

        Assertions.assertEquals(expectedPosts, result);
        Mockito.verify(postRepository)
               .getPostsByUser(userId);
    }

    @Test
    void addLike_should_addLike_and_updatePost() {
        Like like = new Like();
        Post post = Helpers.createMockPost();

        int initialLikesCount = post.getLikesCount();

        Like result = postService.addLike(like, post);

        Assertions.assertEquals(like, result);
        Assertions.assertEquals(initialLikesCount + 1, post.getLikesCount());
        Assertions.assertTrue(post.getLikes()
                                  .contains(like));
        Mockito.verify(postRepository)
               .updatePost(post);
    }




    @Test
    void deleteTagFromPost_shouldRemoveTagAndUpdatePost() {
        // Arrange
        User adminUser = Helpers.createMockAdmin();
        Post post = Helpers.createMockPost();
        Tag tag = Helpers.createMockTag();
        post.getTags().add(tag);

        // Act
        postService.deleteTagFromPost(tag, post, adminUser);

        // Assert
        Assertions.assertFalse(post.getTags().contains(tag));
        Mockito.verify(postRepository).updatePost(post);
    }

    @Test
    void deleteTagFromPost_shouldThrowWhenUserNotAuthorized() {
        // Arrange
        User regularUser = Helpers.createMockUser();
        Post post = Helpers.createMockPost();
        Tag tag = Helpers.createMockTag();



        // Act & Assert
        Assertions.assertThrows(AuthorizationException.class,
                () -> postService.deleteTagFromPost(tag, post, regularUser));
    }

    @Test
    void updatePostTag_shouldReplaceOldTagWithNewTagAndUpdatePost() {
        // Arrange
        User adminUser = Helpers.createMockAdmin();
        Post post = Helpers.createMockPost();
        Tag oldTag = Helpers.createMockTag();
        Tag newTag = Helpers.createMockTag();
        post.getTags().add(oldTag);

        Mockito.when(tagRepository.createOrUpdateTag(newTag)).thenReturn(newTag);

        // Act
        Tag result = postService.updatePostTag(oldTag, post, adminUser, newTag);

        // Assert
        Assertions.assertEquals(newTag, result);
        Assertions.assertTrue(post.getTags().contains(newTag));
        Assertions.assertFalse(post.getTags().contains(oldTag));
        Mockito.verify(tagRepository).createOrUpdateTag(newTag);
        Mockito.verify(postRepository).updatePost(post);
    }

    @Test
    void updatePostTag_shouldThrowWhenUserNotAuthorized() {
        // Arrange
        User regularUser = Helpers.createMockUser();
        Post post = Helpers.createMockPost();
        Tag oldTag = Helpers.createMockTag();
        Tag newTag = Helpers.createMockTag();

        // Act & Assert
        Assertions.assertThrows(AuthorizationException.class,
                () -> postService.updatePostTag(oldTag, post, regularUser, newTag));
    }


}

