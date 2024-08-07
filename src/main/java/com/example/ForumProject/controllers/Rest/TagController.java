package com.example.ForumProject.controllers.Rest;

import com.example.ForumProject.models.dto.PostSummaryDTO;
import com.example.ForumProject.models.dto.TagDTO;
import com.example.ForumProject.models.helpers.TagMapper;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.Tag;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.services.contracts.TagService;
import com.example.ForumProject.services.contracts.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
@io.swagger.v3.oas.annotations.tags.Tag(name = "TagController", description = "Endpoints for managing tags")
public class TagController {


    private final TagService tagService;
    private final PostService postService;
    private final TagMapper tagMapper;
    private final UserService userService;

    public TagController(TagService tagService, PostService postService, TagMapper tagMapper, UserService userService) {
        this.tagService = tagService;
        this.postService = postService;
        this.tagMapper = tagMapper;
        this.userService = userService;
    }
    @Operation(summary = "Get all tags", description = "Retrieve a list of all tags")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tags"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.findAllTags();
    }

    @Operation(summary = "Get a tag by name", description = "Retrieve a tag by its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the tag"),
            @ApiResponse(responseCode = "404", description = "Tag not found", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/{name}")
    public Tag getTagByName(@Parameter(description = "Name of the tag to retrieve") @PathVariable String name) {
            Optional<Tag> tag = tagService.findByName(name);
            return tag.get();

    }
    @Operation(summary = "Get posts by tag ID", description = "Retrieve posts associated with a tag by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the posts"),
            @ApiResponse(responseCode = "404", description = "Tag not found", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("{id}/posts")
    public List<PostSummaryDTO> getPostsByTagId(@Parameter(description = "ID of the tag to retrieve posts for") @PathVariable int id) {

            Tag tag = tagService.findById(id);
            List<PostSummaryDTO> posts = postService.findPostsByTagId(tag.getId());
            return posts;
    }
    @Operation(summary = "Get posts by tag name", description = "Retrieve posts associated with a tag by its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the posts"),
            @ApiResponse(responseCode = "404", description = "Tag not found", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/name/posts")
    public List<PostSummaryDTO> getPostsByTagName(@Parameter(description = "Name of the tag to retrieve posts for") @RequestParam String name) {

            Optional<Tag> tag = tagService.findByName(name);
            List<PostSummaryDTO> posts = postService.findPostsByTagId(tag.get().getId());
            return posts;

    }

    @Operation(summary = "Create a new tag", description = "Create a new tag with the given details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tag created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", description = "Tag already exists", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping
    public Tag createTag( @Valid @RequestBody TagDTO tagDTO) {

            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            Tag tag = tagMapper.tagFromDTO(tagDTO);
            return tagService.createTag(tag, user);
    }
    @Operation(summary = "Update a tag by ID", description = "Update the details of a tag by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the tag"),
            @ApiResponse(responseCode = "404", description = "Tag not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    })
    @PutMapping("/{id}")
    public Tag updateTag(@PathVariable int id, @Valid @RequestBody TagDTO tagDTO) {

            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            Tag tag = tagService.findById(id);
            Tag newTag = tagMapper.tagFromDTO(tagDTO);
            tagService.updateTag(tag, user, newTag);
            return tag;

    }
    @Operation(summary = "Delete a tag by ID", description = "Delete a tag by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the tag"),
            @ApiResponse(responseCode = "404", description = "Tag not found", content = @Content(schema = @Schema(hidden = true)))
    })
    @DeleteMapping("/{tagId}")
    public void deleteTag(@PathVariable int tagId) {

        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Tag tag = tagService.findById(tagId);
        tagService.deleteTag(tag,user);

    }
}
