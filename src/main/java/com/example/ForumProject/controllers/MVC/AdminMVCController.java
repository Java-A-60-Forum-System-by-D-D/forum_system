package com.example.ForumProject.controllers.MVC;

import com.example.ForumProject.services.contracts.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMVCController {
    private final UserService userService;

    public AdminMVCController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAdminPortal(Model model) {
        System.out.println("Admin portal");
        model.addAttribute("users", userService.getUsers());
        System.out.println("2");

        return "AdminPortalView";
    }

}
