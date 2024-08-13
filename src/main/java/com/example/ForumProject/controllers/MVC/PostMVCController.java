package com.example.ForumProject.controllers.MVC;


import com.example.ForumProject.models.dto.CommentDTO;
import com.example.ForumProject.models.dto.FilterPostsDTO;
import com.example.ForumProject.models.dto.PostDTO;
import com.example.ForumProject.models.dto.PostSummaryDTO;
import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.models.helpers.PostMapper;
import com.example.ForumProject.models.helpers.TagMapper;
import com.example.ForumProject.models.persistentClasses.Comment;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.Tag;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.services.contracts.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class PostMVCController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;
    private final CategoriesService categoriesService;
    private final LikeService likeService;
    private final TagMapper tagMapper;
    private final TagService tagService;

    public PostMVCController(PostService postService, PostMapper postMapper, UserService userService, CategoriesService categoriesService, LikeService likeService, TagMapper tagMapper, TagService tagService) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.userService = userService;
        this.categoriesService = categoriesService;
        this.likeService = likeService;
        this.tagMapper = tagMapper;
        this.tagService = tagService;
    }


    @GetMapping
    public String getAllPosts(@ModelAttribute("filterOptions") FilterPostsDTO filterPostsDTO, Principal principal, Model model) {

        User user = userService.getUserByUsername(principal.getName());
        FilterOptionsPosts filterOptionsPosts = new FilterOptionsPosts(filterPostsDTO.getTitle(),
                filterPostsDTO.getContent(),
                filterPostsDTO.getUserId(),
                filterPostsDTO.getTagId(),
                filterPostsDTO.getSortBy(),
                filterPostsDTO.getSortOrder());


        List<PostSummaryDTO> allPosts = postService.getPosts(user, filterOptionsPosts);
        model.addAttribute("allPosts", allPosts);
        model.addAttribute("filterOptions", filterOptionsPosts);

        return "AllPosts";

    }


    @GetMapping("/myPosts")
    public String getUsersPosts(Principal principal, Model model) {
        User user = userService.getUserByUsername(principal.getName());
        List<PostSummaryDTO> posts = postService.getPostsByUser(user.getId());


        model.addAttribute("posts", posts);
        model.addAttribute("username", user.getUsername());

        return "UserPosts";
    }


    @GetMapping("/createForm")
    public String showCreateForm(Model model) {
        model.addAttribute("postDTO", new PostDTO());
        model.addAttribute("categories", categoriesService.getAllCategories());
        return "PostCreationForm";
    }


    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute("postDTO") PostDTO postDTO,
                             BindingResult bindingResult,
                             Principal principal) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "PostCreationForm";
        }
        User author = userService.getUserByUsername(principal.getName());
        Post post = postMapper.createFromDto(postDTO, author);

        postService.createPost(post, author);

        // Handle tags

        Set<Tag> tags = postDTO.getTags()
                               .stream()
                               .map(tagMapper::tagFromString)
                               .map(tag -> tagService.createTag(tag, author))
                               .collect(Collectors.toSet());


        post.setTags(tags);
        postService.updatePost(post);

        return "redirect:/posts";
    }


    @GetMapping("/{id}")
    public String showPostDetails(@PathVariable int id, Model model, Principal principal) {
        Post post = postService.getPostByPostId(id);
        User user = userService.getUserByUsername(principal.getName());
        boolean isLiked = likeService.isPostLikedByUser(post.getId(), user.getId());
        Set<Comment> comments = post.getComments();


        model.addAttribute("post", post);
        model.addAttribute("isLiked", isLiked);
        model.addAttribute("comments", comments);
        model.addAttribute("commentDTO", new CommentDTO());


        return "PostDetails";
    }


}
