package com.example.ForumProject.Controllers;


import com.example.ForumProject.Services.PostService;
import com.example.ForumProject.Services.UserService;
import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.helpers.LoggedUser;
import com.example.ForumProject.helpers.PostMapper;
import com.example.ForumProject.helpers.UserMapper;
import com.example.ForumProject.models.Post;
import com.example.ForumProject.models.User;
import com.example.ForumProject.models.dto.PostDTO;
import com.example.ForumProject.models.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/users")
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


    @GetMapping
    public List<User> getAll() {
        return userService.getUsers();
    }

    @GetMapping("/id/{id}")
    public User getById(@PathVariable int id) {
        try {
            return userService.getUserById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/username/{username}")
    public User getByUsername(@PathVariable String username) {
        try {
            return userService.getUserByUsername(username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/first_name/{firstName}")
    public User getByFirstName(@PathVariable String firstName) {
        try {
            return userService.getUserByFirstName(firstName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }





    @PostMapping("/username/{username}/posts")
    public Post createPost(@Valid @RequestBody PostDTO postDTO, @Valid @PathVariable String username, @RequestHeader HttpHeaders headers) {

        try {

            Post post = postMapper.createFromDto(postDTO, getByUsername(username));
            return postService.createPost(post);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }


    }

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
