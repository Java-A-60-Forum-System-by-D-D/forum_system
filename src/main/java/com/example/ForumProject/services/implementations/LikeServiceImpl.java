package com.example.ForumProject.services.implementations;

import com.example.ForumProject.exceptions.EntityDuplicateException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.persistentClasses.Like;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.repositories.contracts.LikeRepository;
import com.example.ForumProject.services.contracts.LikeService;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.services.contracts.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final PostService postService;
    private final UserService userService;

    public LikeServiceImpl(LikeRepository likeRepository, PostService postService, UserService userService) {
        this.likeRepository = likeRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public Like likePost(int userId, int postId) {
        Optional<Like> existingLike = likeRepository.findByPostIdAndUserId(postId, userId);
        if (existingLike.isPresent()) {
            throw new EntityDuplicateException("User", String.valueOf(userId));
        }
        Post post = postService.getPostById(postId);
        User user = userService.getUserById(userId);
        Like like = new Like(post, user);
        return likeRepository.save(like);
    }

    public void unlikePost(int userId, int postId) {
        Optional<Like> like = likeRepository.findByPostIdAndUserId(postId, userId);
        if (!like.isPresent()) {
            throw new EntityNotFoundException("User", String.valueOf(userId));
        }

        likeRepository.delete(like.get());
    }

    public int getLikesCount(int postId) {
        return likeRepository.countByPostId(postId);
    }
}
