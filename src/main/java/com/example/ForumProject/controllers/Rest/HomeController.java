package com.example.ForumProject.controllers.Rest;


import com.example.ForumProject.models.dto.PostSummaryDTO;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.services.implementations.PostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "HomeMVCController", description = "Home page retrieve 10 most commented posts and 10 recently created posts.")
public class HomeController {

    private final PostService postService;
    private final PostServiceImpl postServiceTest;


    @Autowired
    public HomeController(PostService postService, PostServiceImpl postServiceTest) {
        this.postService = postService;

        this.postServiceTest = postServiceTest;
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
    public Map<String,List<PostSummaryDTO>> get10MostCommentedPosts() {

        Map<String, List<PostSummaryDTO>> result = new HashMap<>();

        List<PostSummaryDTO> mostCommentedPosts = postService.get10MostCommentedPosts();
        result.put("MostCommented", mostCommentedPosts);
        List<PostSummaryDTO> mostRecentlyCreatedPosts = postService.get10MostRecentlyAddedPosts();
        result.put("MostRecent", mostRecentlyCreatedPosts);
        return result;

    }




}
