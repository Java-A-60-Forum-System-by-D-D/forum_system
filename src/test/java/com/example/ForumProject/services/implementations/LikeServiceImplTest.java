package com.example.ForumProject.services.implementations;

import com.example.ForumProject.exceptions.EntityDuplicateException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.persistentClasses.Like;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.repositories.contracts.LikeRepository;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.services.contracts.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {
    @Mock
    private LikeRepository likeRepository;
    @InjectMocks
    private LikeServiceImpl likeService;
    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Test
    void likePost_should_throw_when_like_already_exists() {
        int userId = 1;
        int postId = 1;
        when(likeRepository.findByPostIdAndUserId(postId, userId)).thenReturn(Optional.of(new Like()));

        EntityDuplicateException thrown = assertThrows(EntityDuplicateException.class, () -> {
            likeService.likePost(userId, postId);
        });

        assertEquals("User with id 1 already likes this post.", thrown.getMessage());
    }

    @Test
    void likePost_should_save_like_when_not_exists() {
        int userId = 1;
        int postId = 1;
        Post post = new Post();
        User user = new User();
        Like like = new Like(post, user);

        when(likeRepository.findByPostIdAndUserId(postId, userId)).thenReturn(Optional.empty());
        when(postService.getPostSummaryByPostId(postId)).thenReturn(post);
        when(userService.getUserById(userId)).thenReturn(user);
        when(likeRepository.save(any(Like.class))).thenReturn(like);

        Like result = likeService.likePost(userId, postId);

        assertNotNull(result);
        verify(postService).addLike(like, post);
        verify(likeRepository).save(like);
    }

    @Test
    void unlikePost_should_throw_when_like_does_not_exist() {
        int userId = 1;
        int postId = 1;
        when(likeRepository.findByPostIdAndUserId(postId, userId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            likeService.unlikePost(userId, postId);
        });

        assertEquals("User with id 1 doesn't like this post", thrown.getMessage());
    }

    @Test
    void unlikePost_should_delete_like_when_exists() {
        int userId = 1;
        int postId = 1;
        Post post = new Post();
        User user = new User();
        Like like = new Like(post, user);
        when(likeRepository.findByPostIdAndUserId(postId, userId)).thenReturn(Optional.of(like));
        when(postService.getPostSummaryByPostId(postId)).thenReturn(post);

        likeService.unlikePost(userId, postId);

        verify(likeRepository).delete(like);
        verify(postService).deleteLike(like, post);
    }

    @Test
    void getLikesCount_should_return_count() {
        int postId = 1;
        int expectedCount = 5;
        when(likeRepository.countByPostId(postId)).thenReturn(expectedCount);
        Integer count = likeService.getLikesCount(postId);
        assertEquals(expectedCount, count);
    }
}