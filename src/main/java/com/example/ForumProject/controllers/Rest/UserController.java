package com.example.ForumProject.controllers.Rest;


import com.example.ForumProject.models.filterOptions.FilterOptionsComments;
import com.example.ForumProject.models.filterOptions.FilterOptionsUsersPosts;
import com.example.ForumProject.models.persistentClasses.Comment;
import com.example.ForumProject.services.contracts.CommentService;
import com.example.ForumProject.services.contracts.UserService;
import com.example.ForumProject.models.helpers.UserMapper;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@Tag(name = "UserController", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final CommentService commentService;


    @Autowired
    public UserController(UserService userService, UserMapper userMapper, CommentService commentService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.commentService = commentService;
    }

    @Operation(summary = "Get posts by user ID", description = "Retrieve posts created by a specific user, with optional filtering by title, content, tag, sorting by specified fields.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user's posts"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Forbidden - User does not have permission", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/id/{user_id}/posts")
    public List<Post> getUsersPosts(@PathVariable int user_id, @Parameter(description = "Filter posts by title") @RequestParam(required = false) String title,
                                    @Parameter(description = "Filter posts by content") @RequestParam(required = false) String content,
                                    @Parameter(description = "Filter posts by tag") @RequestParam(required = false) Integer tagId,
                                    @RequestParam(required = false) String sortBy,
                                    @RequestParam(required = false) String sortOrder) {

        FilterOptionsUsersPosts filterOptionsUsersPosts = new FilterOptionsUsersPosts(title, content, tagId, sortBy, sortOrder);
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String username = authentication.getName();
        User userPosts = userService.getUserById(user_id);
        return userService.getPostsByUser(userPosts, filterOptionsUsersPosts);
    }


    @Operation(summary = "Get comments by user ID", description = "Retrieve comments made by a specific user, with optional sorting by specified fields.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user's comments"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Forbidden - User does not have permission", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/id/{user_id}/comments")
    public List<Comment> getUsersComments(@Valid @PathVariable int user_id,
                                          @RequestParam(required = false) String sortBy,
                                          @RequestParam(required = false) String sortOrder) {
        FilterOptionsComments filterOptionsComments = new FilterOptionsComments(sortBy, sortOrder);
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String username = authentication.getName();
        User userPosts = userService.getUserById(user_id);
        return commentService.getCommentsByUser(userPosts, filterOptionsComments);

    }

    @Operation(summary = "Update user information", description = "Update the details of a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/id/{id}")
    public User updateUser(@Valid @RequestBody UserDTO userDTO, @Valid @PathVariable int id) {

        User user = userMapper.createUserFromDto(id, userDTO);
        return userService.updateUser(user);
    }


}
