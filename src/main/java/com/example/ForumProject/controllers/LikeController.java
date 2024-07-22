package com.example.ForumProject.controllers;

import com.example.ForumProject.exceptions.EntityDuplicateException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.persistentClasses.Like;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.services.contracts.LikeService;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.services.contracts.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/posts")
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;
    private final PostService postService;

    public LikeController(LikeService likeService, UserService userService, PostService postService) {
        this.likeService = likeService;
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("/{postId}/like")
    public Like likePost(
            @PathVariable int postId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String username = authentication.getName();

            User user = userService.getUserByUsername(username);
            Like like = likeService.likePost(user.getId(), postId);
            return like;
        } catch (EntityDuplicateException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{postId}/like")
    public void unlikePost(
            @PathVariable int postId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            likeService.unlikePost(user.getId(), postId);

        } catch (EntityNotFoundException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{postId}/likes")
    public Integer getLikesCount(@PathVariable int postId) {
        Integer likesCount = likeService.getLikesCount(postId);
        return likesCount;
    }
}