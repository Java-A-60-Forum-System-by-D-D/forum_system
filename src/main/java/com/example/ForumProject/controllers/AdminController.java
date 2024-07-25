package com.example.ForumProject.controllers;

import com.example.ForumProject.services.contracts.AdminService;
import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.services.contracts.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Endpoints for managing admin privileges")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;


    @Autowired
    public AdminController(AdminService adminService, UserService userService) {

        this.adminService = adminService;
        this.userService = userService;
    }

    @PostMapping("/admin-privileges/{user_id}")
    @Operation(summary = "Grant admin rights to a user", description = "Grant admin rights to a specific user by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin rights granted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", description = "Conflict in granting admin rights", content = @Content(schema = @Schema(hidden = true)))
    })
    public User grantAdminRights(@Parameter(description = "ID of the user to grant admin rights") @PathVariable int user_id) {

        return adminService.grantAdminRights(user_id);

    }

    @PostMapping("/moderator-privileges/{user_id}")
    @Operation(summary = "Grant moderator rights to a user", description = "Grant moderator rights to a specific user by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moderator rights granted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", description = "Conflict in granting moderator rights", content = @Content(schema = @Schema(hidden = true)))
    })
    public User grantModeratorRights(@Parameter(description = "ID of the user to grant moderator rights") @PathVariable int user_id) {
        return adminService.grantModeratorRights(user_id);

    }

    @DeleteMapping("/admin-privileges/{user_id}")
    @Operation(summary = "Revoke admin rights from a user", description = "Revoke admin rights from a specific user by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin rights revoked successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", description = "Conflict in revoking admin rights", content = @Content(schema = @Schema(hidden = true)))
    })
    public User revokeAdminRights(@Parameter(description = "ID of the user to revoke admin rights") @PathVariable int user_id) {
        return adminService.revokeAdminRights(user_id);

    }

    @DeleteMapping("/moderator-privileges/{user_id}")
    @Operation(summary = "Revoke moderator rights from a user", description = "Revoke moderator rights from a specific user by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moderator rights revoked successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", description = "Conflict in revoking moderator rights", content = @Content(schema = @Schema(hidden = true)))
    })
    public User revokeModeratorRights(@Parameter(description = "ID of the user to revoke moderator rights") @PathVariable int user_id) {
        return adminService.revokeModeratorRights(user_id);

    }

    @PostMapping("/block/{user_id}")
    @Operation(summary = "Block a user", description = "Block a specific user by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User blocked successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", description = "Conflict in blocking the user", content = @Content(schema = @Schema(hidden = true)))
    })
    public User blockUser(@Parameter(description = "ID of the user to block") @PathVariable int user_id) {
        return adminService.blockUser(user_id);

    }

    @DeleteMapping("/block/{user_id}")
    @Operation(summary = "Unblock a user", description = "Unblock a specific user by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User unblocked successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", description = "Conflict in unblocking the user", content = @Content(schema = @Schema(hidden = true)))
    })
    public User unblockUser(@Parameter(description = "ID of the user to unblock") @PathVariable int user_id) {

        return adminService.unblockUser(user_id);

    }


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
        return userService.getUserById(id);
    }

    @Operation(summary = "Get user by username", description = "Retrieve a user by their username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/username/{username}")
    public User getByUsername(@Parameter(description = "Username of the user to be retrieved", required = true) @PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @Operation(summary = "Get user by first name", description = "Retrieve a user by their first name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/first_name/{firstName}")
    public User getByFirstName(@Parameter(description = "First name of the user to be retrieved", required = true) @PathVariable String firstName) {
        return userService.getUserByFirstName(firstName);

    }

    @Operation(summary = "Get user by email", description = "Retrieve a user by their email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/email/{userEmail}")
    public User getByEmail(@Parameter(description = "Email of the user to be retrieved", required = true) @PathVariable String userEmail) {
        return userService.getUserByEmail(userEmail);

    }


}
