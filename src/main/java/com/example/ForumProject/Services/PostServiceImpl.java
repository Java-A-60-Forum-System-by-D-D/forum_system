package com.example.ForumProject.Services;

import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.models.*;
import com.example.ForumProject.repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {
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

        authenticationValidator(user, existingPost);

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


        authenticationValidator(user, getPostById(id));

        postRepository.deletePost(id);
    }

    @Override
    public List<Post> getPostsByUser(int id) {
        return postRepository.getPostsByUser(id);
    }


    private static void authenticationValidator(User user, Post existingPost) {

        Set<UserRole> userRoleSet = user.getUserRole();
        UserRole userRoleAdmin = new UserRole(UserRoleEnum.ADMIN);

        if (!userRoleSet
                .contains(userRoleAdmin) && !Objects.equals(existingPost.getUser()
                .getUsername(), user.getUsername())) {
            throw new AuthorizationException("You have no permission to perform this action");
        }
    }


}
