package com.example.ForumProject.controllers;

import com.example.ForumProject.services.contracts.AdminService;
import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.services.contracts.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;
    private final PostService postService;


    @Autowired
    public AdminController(AdminService adminService, UserService userService, PostService postService) {

        this.adminService = adminService;
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("/admin-privileges/{user_id}")
    public User grantAdminRights(@PathVariable int user_id) {

        try {
            return adminService.grantAdminRights(user_id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

    }

    @PostMapping("/moderator-privileges/{user_id}")
    public User grantModeratorRights(@PathVariable int user_id) {
        try {
            return adminService.grantModeratorRights(user_id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

    }

    @DeleteMapping("/admin-privileges/{user_id}")
    public User revokeAdminRights(@PathVariable int user_id) {

        try {
            return adminService.revokeAdminRights(user_id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

    }

    @DeleteMapping("/moderator-privileges/{user_id}")
    public User revokeModeratorRights(@PathVariable int user_id) {

        try {
            return adminService.revokeModeratorRights(user_id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

    }

    @PostMapping("/block/{user_id}")
    public User blockUser(@PathVariable int user_id) {

        try {
            return adminService.blockUser(user_id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }


    }

    @DeleteMapping("/block/{user_id}")
    public User unblockUser(@PathVariable int user_id) {

        try {
            return adminService.unblockUser(user_id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }


    }



//    @GetMapping("/users")
//    public List<User> getAll() {
//        return userService.getUsers();
//    }
//
//    @GetMapping("users/id/{id}")
//    public User getById(@PathVariable int id) {
//        try {
//            return userService.getUserById(id);
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        }
//    }
//
//    @GetMapping("/users/username/{username}")
//    public User getByUsername(@PathVariable String username) {
//        try {
//            return userService.getUserByUsername(username);
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        }
//    }
//
//    @GetMapping("/users/first_name/{firstName}")
//    public User getByFirstName(@PathVariable String firstName) {
//        try {
//            return userService.getUserByFirstName(firstName);
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        }
//
//    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponse(responseCode = "200", description = "List of users", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    @GetMapping
    public List<User> getAll() {
        return userService.getUsers();
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/id/{id}")
    public User getById(@Parameter(description = "ID of the user to be retrieved", required = true) @PathVariable int id) {
        try {
            return userService.getUserById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Operation(summary = "Get user by username", description = "Retrieve a user by their username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/username/{username}")
    public User getByUsername(@Parameter(description = "Username of the user to be retrieved", required = true) @PathVariable String username) {
        try {
            return userService.getUserByUsername(username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Operation(summary = "Get user by first name", description = "Retrieve a user by their first name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/first_name/{firstName}")
    public User getByFirstName(@Parameter(description = "First name of the user to be retrieved", required = true) @PathVariable String firstName) {
        try {
            return userService.getUserByFirstName(firstName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }




}
