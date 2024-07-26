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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/posts/{postId}/comments")
@Tag(name = "CommentController", description = "Endpoints for managing comments")
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
    @Operation(summary = "Get all comments for a post", description = "Get a list of all comments for a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of comments"),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(schema = @Schema(hidden = true)))
    })
    public List<Comment> getAllComments(@Parameter(description = "ID of the post to get comments for") @PathVariable int postId) {

        return postService.getPostById(postId)
                          .getComments()
                          .stream()
                          .filter(comment -> comment.getParentCommentId() == null)
                          .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a comment by ID", description = "Get the details of a comment by its ID for a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the comment"),
            @ApiResponse(responseCode = "404", description = "Comment or post not found", content = @Content(schema = @Schema(hidden = true)))
    })
    public Comment getCommentById(@Parameter(description = "ID of the post") @PathVariable int postId,
                                  @Parameter(description = "ID of the comment") @PathVariable int id) {

        Post post = postService.getPostById(postId);
        return commentService.getCommentById(id, post);
    }

    @PostMapping
    @Operation(summary = "Create a new comment", description = "Create a new comment for a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    })
    public Comment createComment(@Parameter(description = "ID of the post to add a comment to") @PathVariable int postId,
                                 @Valid @RequestBody CommentDTO commentDTO) {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Post post = postService.getPostById(postId);
        Comment comment = commentMapper.createFromDto(post, commentDTO, user);
        return commentService.createComment(comment);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a comment by ID", description = "Update the details of a comment by its ID for a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the comment"),
            @ApiResponse(responseCode = "404", description = "Comment or post not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    })
    public Comment updateComment(@Parameter(description = "ID of the post") @PathVariable int postId,
                                 @Valid @RequestBody CommentDTO commentDto,
                                 @Parameter(description = "ID of the comment to update") @PathVariable int id) {

        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Post post = postService.getPostById(postId);
        Comment existingComment = commentService.getCommentById(id, post);


        Comment updateComment = commentMapper.updateFromDto(existingComment, commentDto, user);
        return commentService.updateComment(updateComment, user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a comment by ID", description = "Delete a comment by its ID for a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the comment"),
            @ApiResponse(responseCode = "404", description = "Comment or post not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    })
    public void deleteComment(@Parameter(description = "ID of the post") @PathVariable int postId,
                              @Parameter(description = "ID of the comment to delete") @PathVariable int id) {

        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Post post = postService.getPostById(postId);
        Comment comment = commentService.getCommentById(id, post);

        commentService.deleteComment(id, user);

    }
}
