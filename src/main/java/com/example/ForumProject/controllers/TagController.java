package com.example.ForumProject.controllers;

import com.example.ForumProject.exceptions.EntityDuplicateException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.dto.TagDTO;
import com.example.ForumProject.models.helpers.TagMapper;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.Tag;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.services.contracts.TagService;
import com.example.ForumProject.services.contracts.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
public class TagController {


    private final TagService tagService;
    private final PostService postService;
    private final TagMapper tagMapper;
    private final UserService userService;

    public TagController(TagService tagService, PostService postService, TagMapper tagMapper, UserService userService) {
        this.tagService = tagService;
        this.postService = postService;
        this.tagMapper = tagMapper;
        this.userService = userService;
    }

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.findAllTags();
    }


    @GetMapping("/{name}")
    public Tag getTagByName(@PathVariable String name) {
        try {
            Optional<Tag> tag = tagService.findByName(name);
            return tag.get();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PostMapping
    public Tag createTag( @Valid @RequestParam TagDTO tagDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            Tag tag = tagMapper.tagFromDTO(tagDTO);
            return tagService.createTag(tag, user);
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    e.getMessage()
            );
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }

    @PutMapping("/{id}")
    public Tag updateTag(@PathVariable int id, @Valid @RequestBody TagDTO tagDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            Tag tag = tagService.findById(id);
            Tag newTag = tagMapper.tagFromDTO(tagDTO);
            tagService.updateTag(tag, user, newTag);
            return tag;
        }catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @GetMapping("{id}/posts")
    public List<Post> getPostsByTagId(@PathVariable int id) {
        try {
            Tag tag = tagService.findById(id);
            List<Post> posts = postService.findPostsByTagId(tag.getId());
            return posts;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }

    }
    @GetMapping("/name/posts")
    public List<Post> getPostsByTagName(@RequestParam String name) {
        try{
            Optional<Tag> tag = tagService.findByName(name);
            List<Post> posts = postService.findPostsByTagId(tag.get().getId());
            return posts;
        }catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }

    @DeleteMapping("/{tagId}")
    public void deleteTag(@PathVariable int tagId) {
        try {
            Tag tag = tagService.findById(tagId);
            tagService.deleteTag(tag);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }
}
