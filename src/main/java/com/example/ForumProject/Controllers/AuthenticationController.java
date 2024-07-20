package com.example.ForumProject.Controllers;


import com.example.ForumProject.Services.UserService;
import com.example.ForumProject.helpers.LoggedUser;
import com.example.ForumProject.models.User;
import com.example.ForumProject.models.dto.LoggInUserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;


    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
//        try {
//            User registeredUser = userService.registerNewUser(user);
//            return ResponseEntity.ok("User registered successfully");
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

}
