package com.example.ForumProject.Controllers;

import com.example.ForumProject.Services.PostService;
import com.example.ForumProject.Services.UserService;
import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.helpers.PostMapper;
import com.example.ForumProject.models.Post;
import com.example.ForumProject.models.User;
import com.example.ForumProject.models.dto.PostDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public PostController(PostService postService, PostMapper postMapper, UserService userService, ModelMapper modelMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable int id) {
        try {
            return postService.getPostById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable int id, @Valid @RequestBody PostDTO postDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                                                                 .getAuthentication();
            String username = authentication.getName();
            Post existingPost = postService.getPostById(id);
            User user = userService.getUserByUsername(username);

            Post post = postMapper.fromDto(id, postDTO);
            return postService.updatePost(post, user, existingPost);


        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable int id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                                                                 .getAuthentication();
            String username = authentication.getName();

            User user = userService.getUserByUsername(username);

            postService.deletePost(id, user);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


}
