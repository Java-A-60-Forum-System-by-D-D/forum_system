package com.example.ForumProject.helpers;

import com.example.ForumProject.models.Post;
import com.example.ForumProject.models.User;
import com.example.ForumProject.models.dto.PostDTO;
import jakarta.persistence.Column;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private final ModelMapper modelMapper;


    public PostMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Post createFromDto(PostDTO postDTO, User user) {
        Post post = modelMapper.map(postDTO, Post.class);
        post.setUser(user);
        return post;
    }
}
