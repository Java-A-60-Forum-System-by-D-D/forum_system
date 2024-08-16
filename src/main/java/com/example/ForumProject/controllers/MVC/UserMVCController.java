package com.example.ForumProject.controllers.MVC;

import com.example.ForumProject.models.helpers.UserMapper;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.persistentClasses.UserRoleEnum;
import com.example.ForumProject.services.contracts.CloudinaryImageService;
import com.example.ForumProject.services.contracts.UserService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/profile")
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

    @GetMapping()
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

    @PostMapping("/update/firstName")
    public String updateFirstName(@ModelAttribute("firstName") String firstName,
                                  BindingResult bindingResult,
                                  Principal principal,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("firstNameError", bindingResult.getFieldError("firstName")
                                                                                .getDefaultMessage());
            return "redirect:/profile";
        }

        try {
            User user = userService.getUserByUsername(principal.getName());
            user.setFirstName(firstName);
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("message", "First name updated successfully");
        } catch (ConstraintViolationException e) {
            // Extract the error message from the exception
            redirectAttributes.addFlashAttribute("message", "First name must be between 4 and 32 symbols");
            String errorMessage = e.getConstraintViolations().iterator().next().getMessage();
            redirectAttributes.addFlashAttribute("firstNameError", errorMessage);
        }
        return "redirect:/profile";
    }

    @PostMapping("/update/lastName")
    public String updateLastName(@ModelAttribute("lastName") String lastName,
                                 BindingResult bindingResult,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("lastNameError", bindingResult.getFieldError("lastName").getDefaultMessage());
            return "redirect:/profile";
        }

        try {
            User user = userService.getUserByUsername(principal.getName());
            user.setLastName(lastName);
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("message", "Last name updated successfully");
        } catch (ConstraintViolationException e) {
            redirectAttributes.addFlashAttribute("message", "Last name must be between 4 and 32 symbols");
            redirectAttributes.addFlashAttribute("lastNameError", e.getConstraintViolations().iterator().next().getMessage());
        }
        return "redirect:/profile";
    }

    @PostMapping("/update/email")
    public String updateEmail(@Valid @ModelAttribute("email") String email,
                              BindingResult bindingResult,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("emailError", bindingResult.getFieldError("email").getDefaultMessage());
            return "redirect:/profile";
        }

        try {
            User user = userService.getUserByUsername(principal.getName());
            user.setEmail(email);
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("message", "Email updated successfully");
        } catch (ConstraintViolationException e) {
            redirectAttributes.addFlashAttribute("message", "Email must be unique");
            redirectAttributes.addFlashAttribute("emailError", e.getConstraintViolations().iterator().next().getMessage());
        }
        return "redirect:/profile";
    }

    @PostMapping("/update/photoURL")
    public String updatePhotoURL(@Valid @ModelAttribute("photoURL") MultipartFile photoURL,
                                 BindingResult bindingResult,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("photoURLError", bindingResult.getFieldError("photoURL").getDefaultMessage());
            return "redirect:/profile";
        }

        try {
            User user = userService.getUserByUsername(principal.getName());
            String photoURLPath = cloudinaryImageService.uploadImage(photoURL);
            user.setPhotoURL(photoURLPath);
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("message", "Profile picture updated successfully");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("photoURLError", "Error uploading image: " + e.getMessage());
        } catch (ConstraintViolationException e) {
            redirectAttributes.addFlashAttribute("photoURLError", e.getConstraintViolations().iterator().next().getMessage());
        }
        return "redirect:/profile";
    }

    @PostMapping("/update/password")
    public String updatePassword(@Valid @ModelAttribute("password") String password,
                                 BindingResult bindingResult,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("passwordError", bindingResult.getFieldError("password").getDefaultMessage());
            return "redirect:/profile";
        }

        try {
            User user = userService.getUserByUsername(principal.getName());
            user.setPassword(passwordEncoder.encode(password));
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("message", "Password updated successfully");
        } catch (ConstraintViolationException e) {
            redirectAttributes.addFlashAttribute("message", "Password must have 1 capital letter 1 lowercase letter and 1 number");
            redirectAttributes.addFlashAttribute("passwordError", e.getConstraintViolations().iterator().next().getMessage());
        }
        return "redirect:/profile";
    }

    @PostMapping("/update/phoneNumber")
    public String updatePhoneNumber(@Valid @ModelAttribute("phoneNumber") String phoneNumber,
                                    BindingResult bindingResult,
                                    Principal principal,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("phoneNumberError", bindingResult.getFieldError("phoneNumber").getDefaultMessage());
            return "redirect:/profile";
        }

        try {
            User user = userService.getUserByUsername(principal.getName());
            user.setPhoneNumber(phoneNumber);
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("message", "Phone number updated successfully");
        } catch (ConstraintViolationException e) {
            redirectAttributes.addFlashAttribute("phoneNumberError", e.getConstraintViolations().iterator().next().getMessage());
        }
        return "redirect:/profile";
    }

}
