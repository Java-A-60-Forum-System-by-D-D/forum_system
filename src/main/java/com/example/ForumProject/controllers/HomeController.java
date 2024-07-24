package com.example.ForumProject.controllers;


import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.services.contracts.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HomeController {

    private final PostService postService;


    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public Map<String,List<Post>> get10MostCommentedPosts() {

        Map<String, List<Post>> result = new HashMap<>();

        try {
            List<Post> mostCommentedPosts = postService.get10MostCommentedPosts();
            result.put("MostCommented", mostCommentedPosts);
            List<Post> mostRecentlyCreatedPosts = postService.get10MostRecentlyAddedPosts();
            result.put("MostRecent", mostRecentlyCreatedPosts);
            return result;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }


}
