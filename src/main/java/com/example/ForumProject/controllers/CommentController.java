package com.example.ForumProject.controllers;

import com.example.ForumProject.services.contracts.CommentService;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.services.contracts.UserService;
import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.helpers.CommentMapper;
import com.example.ForumProject.models.persistentClasses.Comment;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.dto.CommentDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;
    private final CommentMapper commentMapper;


    public CommentController(CommentService commentService, PostService postService, UserService userService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
        this.commentMapper = commentMapper;
    }

    @GetMapping
    public List<Comment> getAllComments(@PathVariable int postId) {

        return postService.getPostById(postId)
                          .getComments()
                          .stream()
                          .toList();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable int postId, @PathVariable int id) {
        try {
            Post post = postService.getPostById(postId);
            return commentService.getCommentById(id,post);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }

    @PostMapping
    public Comment createComment(@PathVariable int postId, @Valid @RequestBody CommentDTO commentDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                                                                 .getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            Post post = postService.getPostById(postId);
            Comment comment = commentMapper.createFromDto(post, commentDTO, user);
            return commentService.createComment(comment);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable int postId, @Valid @RequestBody CommentDTO commentDto, @PathVariable int id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                                                                 .getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            Post post = postService.getPostById(postId);
            Comment existingComment = commentService.getCommentById(id,post);


            Comment updateComment = commentMapper.updateFromDto(existingComment, commentDto, user);
            return commentService.updateComment(updateComment, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        } catch (AuthorizationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    ex.getMessage()
            );
        }
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable int postId, @PathVariable int id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                                                                 .getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            Post post = postService.getPostById(postId);
            Comment comment = commentService.getCommentById(id,post);

            commentService.deleteComment(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }

    }
}
