package com.example.ForumProject.helpers;

import com.example.ForumProject.models.Post;
import com.example.ForumProject.models.dto.PostDTO;
import org.modelmapper.ModelMapper;

public class PostMapper {
    private final ModelMapper modelMapper;

    public PostMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
//    public Post createFromDto(PostDTO postDTO){
//        Post post = modelMapper.map(postDTO, Post.class);
//
//    }
}
