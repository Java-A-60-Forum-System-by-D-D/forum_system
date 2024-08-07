package com.example.ForumProject.controllers.MVC;


import com.example.ForumProject.models.dto.PostDTO;
import com.example.ForumProject.models.dto.PostSummaryDTO;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.services.contracts.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/home", "/"})
public class HomeMVCController {

    private final PostService postService;


    @Autowired
    public HomeMVCController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public String getHomePage(Model model) {
        List<PostSummaryDTO> mostCommented = postService.get10MostCommentedPosts()
                                                        .stream()
                                                        .map(this::truncateContent)
                                                        .collect(Collectors.toList());
        model.addAttribute("Top10MostCommented", mostCommented);

        List<PostSummaryDTO> mostRecent = postService.get10MostRecentlyAddedPosts()
                                                     .stream()
                                                     .map(this::truncateContent)
                                                     .collect(Collectors.toList());

        model.addAttribute("Top10MostRecent", mostRecent);

        return "Home";
    }


    private PostSummaryDTO truncateContent(PostSummaryDTO post) {
        if (post.getContent()
                .length() > 100) {
            post.setContent(post.getContent()
                                .substring(0, 100) + "...");
        }
        return post;
    }


}
