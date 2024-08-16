package com.example.ForumProject.controllers.MVC;

import com.example.ForumProject.models.dto.FilterUserDTO;
import com.example.ForumProject.models.filterOptions.FilterOptionsUsers;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.persistentClasses.UserRole;
import com.example.ForumProject.services.contracts.AdminService;
import com.example.ForumProject.services.contracts.AuthenticationService;
import com.example.ForumProject.services.contracts.UserService;
import com.example.ForumProject.services.implementations.AuthenticationServiceMVC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminMVCController {
    private final UserService userService;
    private final AdminService adminService;
    public AdminMVCController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping
    public String showAdminPortal(@ModelAttribute("userFilter")FilterUserDTO filterUserDTO, Model model) {
        FilterOptionsUsers filterOptionsUsers = new FilterOptionsUsers(
                filterUserDTO.getUsername(),
                filterUserDTO.getFirstName(),
                filterUserDTO.getLastName(),
                filterUserDTO.getRole(),
                filterUserDTO.isBlocked()
        );
        List<User> users = userService.getUsers(filterOptionsUsers);
        model.addAttribute("userFilter", filterUserDTO);
        model.addAttribute("users", users);
        return "AdminPortalView";
    }

    @GetMapping("/users/{id}/grantAdmin")
    public String grantAdminRights(@PathVariable int id) {
        adminService.grantAdminRights(id);
        return "redirect:/admin";
    }

    @GetMapping("/users/{id}/revokeAdmin")
    public String revokeAdminRights(@PathVariable int id) {
        adminService.revokeAdminRights(id);
        return "redirect:/admin";
    }


    @GetMapping("/users/{id}/block")
    public String blockUser(@PathVariable int id) {
        adminService.blockUser(id);

        return "redirect:/admin";
    }
    @GetMapping("/users/{id}/unblock")
    public String unblockUser(@PathVariable int id) {
        adminService.unblockUser(id);

        return "redirect:/admin";
    }

}
