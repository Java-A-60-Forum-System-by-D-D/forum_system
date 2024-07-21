package com.example.ForumProject.Controllers;

import com.example.ForumProject.Services.CommentService;
import com.example.ForumProject.Services.PostService;
import com.example.ForumProject.Services.UserService;
import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.helpers.CommentMapper;
import com.example.ForumProject.models.Comment;
import com.example.ForumProject.models.Post;
import com.example.ForumProject.models.User;
import com.example.ForumProject.models.dto.CommentDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/posts/{id}/comments")
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
    public List<Comment> getAllComments(@PathVariable int id) {
        return commentService.getComments();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable int id) {
        try {
            return commentService.getCommentById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }

    @PostMapping
    public Comment createComment(@PathVariable int id, @Valid @RequestBody CommentDTO commentDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            Post post = postService.getPostById(id);
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
            Comment existingComment = commentService.getCommentById(id);
            Post post = postService.getPostById(postId);

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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            Comment comment = commentService.getCommentById(id);
            Post post = postService.getPostById(postId);
            commentService.deleteComment(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }

    }
}
