package com.example.ForumProject.controllers.Rest;

import com.example.ForumProject.services.contracts.AuthenticationService;
import com.example.ForumProject.models.helpers.UserMapper;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.dto.LoggInUserDTO;
import com.example.ForumProject.models.dto.UserDTO;
import com.example.ForumProject.services.contracts.RestAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthenticationController {

    /**
     *
     */
    private RestAuthenticationService authenticationService;
    private UserMapper userMapper;

    @Autowired
    public AuthenticationController(RestAuthenticationService authenticationService, UserMapper userMapper) {
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
    }

    @Operation(
            summary = "Register a new user",
            description = "Create a new user account with the provided details. " +
                    "If you do not have an account, use this endpoint to register. " +
                    "If you already have an account, you should log in using the /login " +
                    "endpoint to receive a JWT token for authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping("/register")
    public User createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userMapper.createUserFromDto(userDTO);
        return authenticationService.createUser(user);

    }

    @Operation(
            summary = "User login",
            description = "Authenticate the user with the provided username and password. " +
                    "If you already have an account, use this endpoint to log in. " +
                    "Upon successful authentication, you will receive a JWT " +
                    "token which should be used for subsequent API requests to access protected endpoints."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully logged in"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping("/login")
    public LoggInUserDTO loginUser(@Valid @RequestBody LoggInUserDTO loggInUserDTO) {
        return authenticationService.loginUser(loggInUserDTO.getUsername(), loggInUserDTO.getPassword());
    }




    /*TODO Implement Logout method*/
}
