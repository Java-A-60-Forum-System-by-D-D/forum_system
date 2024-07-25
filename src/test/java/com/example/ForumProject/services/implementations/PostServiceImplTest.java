package com.example.ForumProject.services.implementations;

import com.example.ForumProject.Helpers;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.repositories.contracts.PostRepository;
import com.example.ForumProject.services.contracts.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {


    @Mock
    PostRepository postRepository;


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
    void updatePost() {

//        Post mockPost = Helpers.createMockPost();
//        Post

    }

    @Test
    void testUpdatePost() {
    }

    @Test
    void createPost() {
    }

    @Test
    void deletePost() {
    }

    @Test
    void getPostsByUser() {
    }

    @Test
    void addLike() {
    }

    @Test
    void deleteLike() {
    }

    @Test
    void findPostsByTagId() {
    }

    @Test
    void addTagToPost() {
    }

    @Test
    void createTag() {
    }

    @Test
    void deleteTagFromPost() {
    }

    @Test
    void updatePostTag() {
    }
}