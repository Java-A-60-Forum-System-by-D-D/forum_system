package com.example.ForumProject.controllers.MVC;


import com.example.ForumProject.models.dto.PostDTO;
import com.example.ForumProject.models.dto.PostSummaryDTO;
import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.models.helpers.PostMapper;
import com.example.ForumProject.models.persistentClasses.Comment;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.services.contracts.CategoriesService;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.services.contracts.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/posts")
public class PostMVCController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;
    private final CategoriesService categoriesService;


    public PostMVCController(PostService postService, PostMapper postMapper, UserService userService, CategoriesService categoriesService) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.userService = userService;
        this.categoriesService = categoriesService;
    }


    @GetMapping
    public String getUsersPosts(Principal principal, Model model) {
        User user = userService.getUserByUsername(principal.getName());
        List<PostSummaryDTO> posts = postService.getPostsByUser(user.getId());
        model.addAttribute("posts", posts);
        model.addAttribute("username", user.getUsername());
        return "Posts";
    }


    @GetMapping("/createForm")
    public String showCreateForm(Model model) {
        model.addAttribute("postDTO", new PostDTO());
        model.addAttribute("categories", categoriesService.getAllCategories());
        return "PostCreationForm";
    }


    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute("postDTO") PostDTO postDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "PostCreationForm";
        }
        User author = userService.getUserByUsername(principal.getName());
        Post post = postMapper.createFromDto(postDTO, author);
        postService.createPost(post, author);

        return "redirect:/posts";
    }
    @GetMapping("/{id}")
    public String showPostDetails(@PathVariable int id, Model model) {
        Post post = postService.getPostByPostId(id);
        model.addAttribute("post", post);
        Set<Comment> comments = post.getComments();
        model.addAttribute("comments", comments);

        return "PostDetails";
    }


}
