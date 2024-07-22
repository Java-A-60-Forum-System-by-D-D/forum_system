package com.example.ForumProject.models.helpers;

import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.dto.PostDTO;
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


    public Post updatePostFromDto(PostDTO postDTO, User user) {
        Post post = modelMapper.map(postDTO, Post.class);
        post.setUser(user);
        return post;
    }


    public Post fromDto(int id, PostDTO postDTO) {

        Post post = modelMapper.map(postDTO, Post.class);
        post.setId(id);
        return post;
    }
}
