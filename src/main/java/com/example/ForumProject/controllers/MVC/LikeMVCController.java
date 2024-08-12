package com.example.ForumProject.controllers.MVC;

import com.example.ForumProject.models.persistentClasses.Like;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.services.contracts.LikeService;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.services.contracts.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/posts/{postId}")
public class LikeMVCController {
    private final LikeService likeService;
    private final PostService postService;
    private final UserService userService;

    public LikeMVCController(LikeService likeService, PostService postService, UserService userService) {
        this.likeService = likeService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/likes")
    public String like(@PathVariable int postId, Model model, Principal principal) {
        if(model.getAttribute("isLike")!= null) {
            Post post = postService.getPostByPostId(postId);
            User user = userService.getUserByUsername(principal.getName());
            Like like = likeService.likePost(user.getId(), post.getId());
            post = postService.getPostByPostId(postId);
            model.addAttribute("post", post);
            model.addAttribute("isLike", like);
        }

        return "PostDetails";
    }
    @DeleteMapping("/likes")
    public String unlike(@PathVariable int postId, Model model, Principal principal) {
        Post post = postService.getPostByPostId(postId);
        User user = userService.getUserByUsername(principal.getName());
        likeService.unlikePost(user.getId(), post.getId());
        post = postService.getPostByPostId(postId);
        model.addAttribute("post", post);
        model.addAttribute("isLike", null);
        return "PostDetails";
    }

}
