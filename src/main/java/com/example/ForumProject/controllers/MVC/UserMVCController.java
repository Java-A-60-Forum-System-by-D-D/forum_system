package com.example.ForumProject.controllers.MVC;

import com.example.ForumProject.models.helpers.UserMapper;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.persistentClasses.UserRoleEnum;
import com.example.ForumProject.services.contracts.CloudinaryImageService;
import com.example.ForumProject.services.contracts.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class UserMVCController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryImageService cloudinaryImageService;

    public UserMVCController(UserService userService, UserMapper userMapper, PasswordEncoder passwordEncoder, CloudinaryImageService cloudinaryImageService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryImageService = cloudinaryImageService;
    }

    @GetMapping("/profile")
    public String getUserById(Principal principal, Model model) {
        User userByUsername = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", userByUsername);
        Boolean isAdmin = false;
        if (userByUsername.getUserRole()
                          .stream()
                          .anyMatch(u -> u.getRole()
                                          .equals(UserRoleEnum.ADMIN))) {
            isAdmin = true;
        }


        model.addAttribute("isAdmin", isAdmin);

        return "UserDetails";
    }
//    @GetMapping("/profile/update")
//    public String updateUser(Principal principal, Model model) {
//        User userByUsername = userService.getUserByUsername(principal.getName());
//        model.addAttribute("user", userMapper.mapUserToUserDTO(userByUsername));
//        return "UserUpdateDetails";
//    }
//
//    @PostMapping("/profile/update")
//    public String updateUser(@Valid @ModelAttribute("user")UserDTO userDTO, Principal principal, BindingResult bindingResult) {
//        if(bindingResult.hasErrors()) {
//            return "UserUpdateDetails";
//        }
//        User user = userService.getUserByUsername(principal.getName());
//        user = userMapper.mapUserDTOToUser(userDTO, user);
//        userService.updateUser(user);
//        return "redirect:/profile";
//    }

    @PostMapping("/profile/update/firstName")
    public String updateFirstName(@RequestParam("firstName") String firstName, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        user.setFirstName(firstName);
        userService.updateUser(user);
        return "redirect:/profile";
    }

    @PostMapping("/profile/update/lastName")
    public String updateLastName(@RequestParam("lastName") String lastName, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        user.setLastName(lastName);
        userService.updateUser(user);
        return "redirect:/profile";
    }

    @PostMapping("/profile/update/email")
    public String updateEmail(@RequestParam("email") String email, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        user.setEmail(email);
        userService.updateUser(user);
        return "redirect:/profile";
    }

    @PostMapping("/profile/update/photoURL")
    public String updatePhotoURL(@RequestParam("photoURL") MultipartFile photoURL, Principal principal) throws IOException {
        User user = userService.getUserByUsername(principal.getName());
        String photoURLPath = cloudinaryImageService.uploadImage(photoURL);
        user.setPhotoURL(photoURLPath);
        userService.updateUser(user);
        return "redirect:/profile";
    }

    @PostMapping("/profile/update/password")
    public String updatePassword(@RequestParam("password") String password, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        user.setPassword(passwordEncoder.encode(password));
        userService.updateUser(user);
        return "redirect:/profile";
    }

    @PostMapping("/profile/update/phoneNumber")
    public String updatePhoneNumber(@RequestParam("phoneNumber") String phoneNumber, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        user.setPhoneNumber(phoneNumber);
        userService.updateUser(user);
        return "redirect:/profile";
    }


}
