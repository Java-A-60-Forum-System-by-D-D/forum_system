package com.example.ForumProject.controllers;


import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.services.contracts.UserService;
import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.helpers.LoggedUser;
import com.example.ForumProject.models.helpers.PostMapper;
import com.example.ForumProject.models.helpers.UserMapper;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.dto.PostDTO;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/users")
@Tag(name = "Users", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final LoggedUser loggedUser;
    private final PostService postService;
    private final PostMapper postMapper;


    @Autowired
    public UserController(UserService userService, UserMapper userMapper, LoggedUser loggedUser, PostService postService, PostMapper postMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.loggedUser = loggedUser;
        this.postService = postService;
        this.postMapper = postMapper;

    }

    @Operation(summary = "Create a new post for a user", description = "Create a new post for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/username/{username}/posts")
    public Post createPost(@Valid @RequestBody PostDTO postDTO, @Valid @PathVariable String username, @RequestHeader HttpHeaders headers) {

        try {

            Post post = postMapper.createFromDto(postDTO, userService.getUserByUsername(username));
            return postService.createPost(post);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }


    }

    @Operation(summary = "Update user information", description = "Update the details of a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/id/{id}")
    public User updateUser(@Valid @RequestBody UserDTO userDTO, @Valid @PathVariable int id) {

        try {
            User user = userMapper.createUserFromDto(id, userDTO);
            return userService.updateUser(user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }


    }


}
