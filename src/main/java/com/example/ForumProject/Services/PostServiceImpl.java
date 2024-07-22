package com.example.ForumProject.Services;

import com.example.ForumProject.models.*;
import com.example.ForumProject.repositories.PostRepository;
import com.example.ForumProject.utility.ValidatorHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    public static final String INVALID_DELETE_COMMAND = "You have no rights to delete this post";
    public static final String INVALID_UPDATE_COMMAND = "You have no rights to update this post";
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public List<Post> getPosts() {
        return postRepository.getPosts();
    }

    @Override
    public Post getPostById(int id) {
        return postRepository.getPostById(id);
    }

    @Override

    public Post updatePost(Post post, User user, Post existingPost) {


        ValidatorHelpers.roleAuthenticationValidator(user,new UserRole(UserRoleEnum.ADMIN),existingPost, INVALID_UPDATE_COMMAND);

        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());

        return postRepository.updatePost(existingPost);
    }


    @Override
    public Post createPost(Post post) {
        return postRepository.createPost(post);
    }

    @Override
    public void deletePost(int id, User user) {


        ValidatorHelpers.roleAuthenticationValidator(user, new UserRole(UserRoleEnum.ADMIN), getPostById(id), INVALID_DELETE_COMMAND);

        postRepository.deletePost(id);
    }

    @Override
    public List<Post> getPostsByUser(int id) {
        return postRepository.getPostsByUser(id);
    }


}
