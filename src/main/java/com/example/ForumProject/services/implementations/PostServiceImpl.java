package com.example.ForumProject.services.implementations;

import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.models.persistentClasses.*;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.repositories.contracts.PostRepository;
import com.example.ForumProject.utility.ValidatorHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {
    public static final String INVALID_DELETE_COMMAND = "You have no rights to delete this post";
    public static final String INVALID_UPDATE_COMMAND = "You have no rights to update this post";
    public static final String INVALID_GET_ALL_POSTS_COMMAND = "Only admins can see all posts";
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public List<Post> getPosts(User user, FilterOptionsPosts filterOptionsPosts) {

        ValidatorHelpers.roleAuthenticationValidator(user, new UserRole(UserRoleEnum.ADMIN), INVALID_GET_ALL_POSTS_COMMAND);


        return postRepository.getPosts(filterOptionsPosts);
    }

    @Override
    public Post getPostById(int id) {
        return postRepository.getPostById(id);
    }

    @Override

    public Post updatePost(Post post, User user, Post existingPost) {


        ValidatorHelpers.roleAuthenticationValidator(user, new UserRole(UserRoleEnum.ADMIN), existingPost, INVALID_UPDATE_COMMAND);

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

    @Override
    public Like addLike(Like like, Post post) {


        post.getLikes()
            .add(like);

        post.setLikesCount(post.getLikes()
                               .size());
        postRepository.updatePost(post);

        return like;

    }

    @Override
    public Like deleteLike(Like like, Post post) {
        post.getLikes()
            .remove(like);
        post.setLikesCount(post.getLikes()
                               .size());
        postRepository.updatePost(post);

        return like;
    }


}
