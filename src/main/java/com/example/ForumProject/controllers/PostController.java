package com.example.ForumProject.controllers;

import com.example.ForumProject.exceptions.EntityDuplicateException;
import com.example.ForumProject.models.dto.TagDTO;
import com.example.ForumProject.models.helpers.TagMapper;
import com.example.ForumProject.models.persistentClasses.Tag;
import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.services.contracts.TagService;
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
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@io.swagger.v3.oas.annotations.tags.Tag(name = "PostController", description = "Endpoints for managing posts")
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;
    private final TagService tagService;
    private final TagMapper tagMapper;


    public PostController(PostService postService, PostMapper postMapper, UserService userService, TagService tagService, TagMapper tagMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.userService = userService;
        this.tagService = tagService;
        this.tagMapper = tagMapper;
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
                                  @Parameter(description = "Filter posts by tag") @RequestParam(required = false) Integer tagId,
                                  @RequestParam(required = false) String sortBy,
                                  @RequestParam(required = false) String sortOrder
    ) {
        FilterOptionsPosts filterOptionsPosts = new FilterOptionsPosts(title, content, userId, tagId, sortBy, sortOrder);
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String username = authentication.getName();
        User loggedUser = userService.getUserByUsername(username);
        return postService.getPosts(loggedUser, filterOptionsPosts);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a post by ID", description = "Get the details of a post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the post"),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(schema = @Schema(hidden = true)))
    })
    public Post getPostById(@Parameter(description = "ID of the post to retrieve") @PathVariable int id) {

        return postService.getPostById(id);

    }

    @Operation(summary = "Get tags for a post", description = "Retrieve all tags associated with a specific post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the tags"),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/{id}/tags")
    public List<Tag> getPostTags(@Parameter(description = "ID of the post to retrieve tags for") @PathVariable int id) {

        Post post = postService.getPostById(id);
        List<Tag> tags = tagService.findTagsByPostId(post.getId());
        return tags;

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
        return postService.createPost(post, user);

    }

    @Operation(summary = "Add a tag to a post", description = "Create a new tag and associate it with a specific post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tag created and associated with the post"),
            @ApiResponse(responseCode = "404", description = "Post or tag not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", description = "Tag already exists", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping("/{id}/tags")
    public Tag createTag(@PathVariable int id, @Valid @RequestBody TagDTO tagDTO) {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Post post = postService.getPostById(id);
        Tag tag = tagMapper.tagFromDTO(tagDTO);
        return postService.createTag(tag, post, user);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a post by ID", description = "Update the details of a post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the post"),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    })
    public Post updatePost(@Parameter(description = "ID of the post to update") @PathVariable int id, @Valid @RequestBody PostDTO postDTO) {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Post existingPost = postService.getPostById(id);

        Post post = postMapper.fromDto(id, postDTO);
        return postService.updatePost(post, user, existingPost);
    }

    @Operation(summary = "Update a tag for a post", description = "Update an existing tag associated with a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the tag"),
            @ApiResponse(responseCode = "404", description = "Post or tag not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    })
    @PutMapping("/{postId}/tags/{tagId}")
    public Tag updatePostTag(@PathVariable int postId, @PathVariable int tagId, @Valid @RequestBody TagDTO tagDTO) {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Tag tag = tagService.findById(tagId);
        Tag newTag = tagMapper.tagFromDTO(tagDTO);
        Post post = postService.getPostById(postId);
        postService.updatePostTag(tag, post, user, newTag);
        return tag;

    }

    @Operation(summary = "Delete a tag from a post", description = "Remove a tag from a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully removed the tag"),
            @ApiResponse(responseCode = "404", description = "Post or tag not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    })
    @DeleteMapping("{postId}/tags/{tagId}")
    public void deleteTagFromPost(@PathVariable int postId, @PathVariable int tagId) {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Post post = postService.getPostById(postId);
        Tag tag = tagService.findById(tagId);
        postService.deleteTagFromPost(tag, post, user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a post by ID", description = "Delete a post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the post"),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    })
    public void deletePost(@Parameter(description = "ID of the post to delete") @PathVariable int id) {

        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String username = authentication.getName();

        User user = userService.getUserByUsername(username);

        postService.deletePost(id, user);

    }

}



