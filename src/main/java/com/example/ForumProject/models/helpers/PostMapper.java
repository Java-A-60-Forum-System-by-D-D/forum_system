package com.example.ForumProject.models.helpers;

import com.example.ForumProject.models.persistentClasses.Category;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.dto.PostDTO;
import com.example.ForumProject.services.contracts.CategoriesService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class PostMapper {
    private final ModelMapper modelMapper;
    private final CategoriesService categoriesService;


    public PostMapper(ModelMapper modelMapper, CategoriesService categoriesService) {
        this.modelMapper = modelMapper;
        this.categoriesService = categoriesService;
    }

    public Post createFromDto(PostDTO postDTO, User user) {
        Post post = modelMapper.map(postDTO, Post.class);
        post.setUser(user);
        Category category = categoriesService.getCategoryById(postDTO.getCategoryNumber());
        post.setCategory(category);
        post.setLikes(new HashSet<>());
        return post;
    }


    public Post updatePostFromDto(PostDTO postDTO, User user) {
        Post post = modelMapper.map(postDTO, Post.class);
        Category category = categoriesService.getCategoryById(postDTO.getCategoryNumber());
        post.setCategory(category);
        post.setUser(user);
        return post;
    }


    public Post fromDto(int id, PostDTO postDTO) {

        Post post = modelMapper.map(postDTO, Post.class);
        post.setId(id);
        return post;
    }
}
