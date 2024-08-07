package com.example.ForumProject.controllers.Rest;

import com.example.ForumProject.models.persistentClasses.Like;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.services.contracts.LikeService;
import com.example.ForumProject.services.contracts.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "LikeController", description = "Operations related to likes on posts")
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;

    public LikeController(LikeService likeService, UserService userService) {
        this.likeService = likeService;
        this.userService = userService;

    }

    @Operation(summary = "Like a post", description = "Like a specific post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post liked successfully"),
            @ApiResponse(responseCode = "400", description = "Post already liked by the user")
    })
    @PostMapping("/{postId}/like")
    public Like likePost(@Parameter(description = "ID of the post to be liked")
                         @PathVariable int postId) {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String username = authentication.getName();

        User user = userService.getUserByUsername(username);
        return likeService.likePost(user.getId(), postId);
    }

    @Operation(summary = "Unlike a post", description = "Remove like from a specific post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post unliked successfully"),
            @ApiResponse(responseCode = "400", description = "Like not found")
    })
    @DeleteMapping("/{postId}/like")
    public void unlikePost(@Parameter(description = "ID of the post to be unliked")
                           @PathVariable int postId) {

        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        likeService.unlikePost(user.getId(), postId);
    }

    @Operation(summary = "Get likes count", description = "Get the number of likes for a specific post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved likes count")
    })
    @GetMapping("/{postId}/likes")
    public Integer getLikesCount(@Parameter(description = "ID of the post to get likes count") @PathVariable int postId) {
        return likeService.getLikesCount(postId);
    }
}