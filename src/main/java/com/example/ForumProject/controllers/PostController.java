package com.example.ForumProject.controllers;

import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.services.contracts.UserService;
import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.helpers.PostMapper;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.dto.PostDTO;
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
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Endpoints for managing posts")
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;


    public PostController(PostService postService, PostMapper postMapper, UserService userService) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.userService = userService;

    }

    @Operation(summary = "Get all posts", description = "Get a list of all posts, optionally filtered by title, content, user, or tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of posts"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping
    public List<Post> getAllPosts(@Parameter(description = "Filter posts by title") @RequestParam(required = false) String title,
                                  @Parameter(description = "Filter posts by content") @RequestParam(required = false) String content,
                                  @Parameter(description = "Filter posts by user") @RequestParam(required = false) Integer userId,
                                  @Parameter(description = "Filter posts by tag") @RequestParam(required = false) Integer tagId) {
        FilterOptionsPosts filterOptionsPosts = new FilterOptionsPosts(title,content,userId,tagId);
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String username = authentication.getName();
        User loggedUser = userService.getUserByUsername(username);
        try {
            return postService.getPosts(loggedUser,filterOptionsPosts);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


    @PostMapping
    @Operation(summary = "Create a new post", description = "Create a new post with the given details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(hidden = true)))
    })
    public Post createPost(@Valid @RequestBody PostDTO postDTO) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Post post = postMapper.createFromDto(postDTO, user);
        return postService.createPost(post);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a post by ID", description = "Get the details of a post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the post"),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(schema = @Schema(hidden = true)))
    })
    public Post getPostById(@Parameter(description = "ID of the post to retrieve") @PathVariable int id) {
        try {
            return postService.getPostById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a post by ID", description = "Update the details of a post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the post"),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    })
    public Post updatePost(@Parameter(description = "ID of the post to update") @PathVariable int id, @Valid @RequestBody PostDTO postDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            Post existingPost = postService.getPostById(id);

            Post post = postMapper.fromDto(id, postDTO);
            return postService.updatePost(post, user, existingPost);


        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a post by ID", description = "Delete a post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the post"),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    })
    public void deletePost(@Parameter(description = "ID of the post to delete") @PathVariable int id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String username = authentication.getName();

            User user = userService.getUserByUsername(username);

            postService.deletePost(id, user);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

    }

}



