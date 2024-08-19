package com.example.ForumProject.controllers.MVC;

import com.example.ForumProject.models.dto.FilterUserDTO;
import com.example.ForumProject.models.dto.PostSummaryDTO;
import com.example.ForumProject.models.filterOptions.FilterOptionsUsers;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.persistentClasses.UserRole;
import com.example.ForumProject.models.persistentClasses.UserRoleEnum;
import com.example.ForumProject.services.contracts.AdminService;
import com.example.ForumProject.services.contracts.AuthenticationService;
import com.example.ForumProject.services.contracts.PostService;
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
    private final PostService postService;

    public AdminMVCController(UserService userService, AdminService adminService, PostService postService) {
        this.userService = userService;
        this.adminService = adminService;
        this.postService = postService;
    }

    @GetMapping
    public String showAdminPortal(@ModelAttribute("userFilter") FilterUserDTO filterUserDTO, Model model) {
        FilterOptionsUsers filterOptionsUsers = new FilterOptionsUsers(
                filterUserDTO.getUsername(),
                filterUserDTO.getFirstName(),
                filterUserDTO.getLastName(),
                filterUserDTO.getRole(),
                filterUserDTO.getIsBlocked()
        );
        List<User> users = userService.getUsers(filterOptionsUsers);
        model.addAttribute("userFilter", filterUserDTO);
        model.addAttribute("users", users);
        return "AdminPortalView";
    }


    @GetMapping("/users/{id}/posts")
    public String getUserPosts(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        List<PostSummaryDTO> posts = postService.getPostsByUser(user.getId());


        model.addAttribute("posts", posts);
        model.addAttribute("username", user.getUsername());

        return "UserPosts";
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

        if (userService.getUserById(id)
                       .getRoles()
                       .contains(new UserRole(UserRoleEnum.ADMIN))) {
            return "errors/BlockError";
        }else{
            adminService.blockUser(id);

            return "redirect:/admin";

        }

    }

    @GetMapping("/users/{id}/unblock")
    public String unblockUser(@PathVariable int id) {
        adminService.unblockUser(id);

        return "redirect:/admin";
    }

}
