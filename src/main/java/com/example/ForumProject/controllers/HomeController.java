package com.example.ForumProject.controllers;


import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.services.contracts.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "HomeController", description = "Home page retrieve 10 most commented posts and 10 recently created posts.")
public class HomeController {

    private final PostService postService;


    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Get the 10 most commented and 10 most recently created posts.
     *
     * @return A map containing two lists of posts: "MostCommented" and "MostRecent"
     */

    @Operation(summary = "Get the 10 most commented and 10 most recently created posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the posts",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)) }),
            @ApiResponse(responseCode = "404", description = "Posts not found",
                    content = @Content) })
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
