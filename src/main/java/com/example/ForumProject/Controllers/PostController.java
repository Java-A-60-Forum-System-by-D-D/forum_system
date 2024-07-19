package com.example.ForumProject.Controllers;

import com.example.ForumProject.Services.PostService;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.Post;
import com.example.ForumProject.models.dto.PostDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getPosts();
    }
    @GetMapping("/{id}")
    public Post getPostById(@PathVariable int id) {
        try{
            return postService.getPostById(id);
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }
//    @PostMapping
//    public Post createPost(@Valid @RequestBody PostDTO postDTO) {
//
//
//    }

}
