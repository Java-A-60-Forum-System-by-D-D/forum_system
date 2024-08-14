package com.example.ForumProject.models.helpers;

import com.example.ForumProject.models.persistentClasses.Category;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.Tag;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.dto.PostDTO;
import com.example.ForumProject.services.contracts.CategoriesService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        post.setTags(new HashSet<>());
        return post;
    }


    public Post updatePostFromDto(PostDTO postDTO, User user,int id) {
        Post post = modelMapper.map(postDTO, Post.class);
        post.setId(id);

        Category category = categoriesService.getCategoryById(postDTO.getCategoryNumber());
        post.setCategory(category);
        post.setUser(user);
        return post;
    }

    public PostDTO toDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        Set<String> tags = post.getTags()
                               .stream()
                               .map(Tag::getName)
                               .collect(Collectors.toSet());

        postDTO.setTags(tags);
        postDTO.setCategoryNumber(post.getCategory().id);
        return postDTO;
    }


    public Post fromDto(int id, PostDTO postDTO) {

        Post post = modelMapper.map(postDTO, Post.class);
        post.setId(id);
        return post;
    }
}
