package com.example.ForumProject.controllers.MVC;


import com.example.ForumProject.models.dto.CommentDTO;
import com.example.ForumProject.models.helpers.CommentMapper;
import com.example.ForumProject.models.persistentClasses.Comment;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.services.contracts.CommentService;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.services.contracts.UserService;
import com.example.ForumProject.services.implementations.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("posts/{postId}/comments")
public class CommentMVCController {


    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final EmailService emailService;

    @Autowired
    public CommentMVCController(PostService postService, UserService userService, CommentService commentService, CommentMapper commentMapper, EmailService emailService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
        this.commentMapper = commentMapper;
        this.emailService = emailService;
    }


    @PostMapping
    public String createCommentOnPost(@PathVariable int postId, Model model, Principal principal,
                                      @Valid @ModelAttribute("commentDTO") CommentDTO commentDTO,
                                      BindingResult bindingResult) {
        User user = userService.getUserByUsername(principal.getName());
        if (bindingResult.hasErrors()) {
            Post post = postService.getPostByPostId(postId);
            model.addAttribute("post", post);
            model.addAttribute("commentDTO", commentDTO);
            model.addAttribute("user", user);
            return "PostDetails";
        }

        Post post = postService.getPostByPostId(postId);
        emailService.sendCommentNotification(post.getUser().getEmail(), post.getTitle());

        Comment comment = commentMapper.createFromDto(post, commentDTO, user);

        commentService.createComment(comment, post);
        return "redirect:/posts/{postId}";

    }

    @PostMapping("/{commentId}")
    public String createCommentOnPostComment(@PathVariable int postId,
                                             @PathVariable int commentId, Model model,
                                             Principal principal,
                                             @Valid @ModelAttribute("commentDTO") CommentDTO commentDTO,
                                             BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            Post post = postService.getPostByPostId(postId);
            model.addAttribute("post", post);
            model.addAttribute("commentDTO", commentDTO);
            return "PostDetails";
        }


        Post post = postService.getPostByPostId(postId);
        User user = userService.getUserByUsername(principal.getName());
        Comment comment = commentService.getCommentById(commentId, post);
        Comment reply = commentMapper.createFromDto(post, commentDTO, user);

        commentService.createComment(reply, post);
        return "redirect:/posts/{postId}";

    }


}
