package com.example.ForumProject.services.implementations;

import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.models.persistentClasses.*;
import com.example.ForumProject.repositories.contracts.TagRepository;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.repositories.contracts.PostRepository;
import com.example.ForumProject.services.contracts.UserService;
import com.example.ForumProject.utility.ValidatorHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostServiceImpl implements PostService {
    public static final String INVALID_DELETE_COMMAND = "You have no rights to delete this post";
    public static final String INVALID_UPDATE_COMMAND = "You have no rights to update this post";
    public static final String INVALID_GET_ALL_POSTS_COMMAND = "Only admins can see all posts";
    public static final String USER_IS_NOT_AUTHOR_OF_THE_POST_OR_ADMIN = "User is not author of the post or admin";
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final UserService userService;


    @Autowired
    public PostServiceImpl(PostRepository postRepository, TagRepository tagRepository, UserService userService) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.userService = userService;
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
    public List<Post> get10MostCommentedPosts() {
        return postRepository.get10MostCommentedPosts();
    }

    @Override
    public List<Post> get10MostRecentlyAddedPosts() {
        return postRepository.get10MostRecentlyAddedPosts();
    }


    @Override
    public Post createPost(Post post, User user) {
        postRepository.checkIfPostWithTitleExistsForUser(post,user);

        user.getPosts()
            .add(post);

        userService.updateUser(user);

        return postRepository.createPost(post);
    }

    @Override
    public Post updatePost(Post post) {
        return postRepository.updatePost(post);
    }

    @Override
    public Post updatePost(Post post, User user, Post existingPost) {


        ValidatorHelpers.roleAuthenticationValidator(user, new UserRole(UserRoleEnum.ADMIN), existingPost, INVALID_UPDATE_COMMAND);
        postRepository.checkIfPostWithTitleExistsForUser(post,user);

        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());

        return postRepository.updatePost(existingPost);
    }




    @Override
    public void deletePost(int id, User user) {


        ValidatorHelpers.roleAuthenticationValidator(user, new UserRole(UserRoleEnum.ADMIN), getPostById(id), INVALID_DELETE_COMMAND);
        user.getPosts()
            .remove(getPostById(id));
        userService.updateUser(user);

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

    @Override
    public List<Post> findPostsByTagId(int id) {
        return postRepository.getPostsByTagId(id);
    }

    @Override
    public Tag addTagToPost(Tag tag, Post post) {
        post.getTags()
            .add(tag);
        postRepository.updatePost(post);
        return tag;
    }

    @Override
    public Tag createTag(Tag tag, Post post, User user) {
        ValidatorHelpers.roleAuthenticationValidator(user, new UserRole(
                UserRoleEnum.ADMIN), post, "The user is not admin or author.");


        if (!post.getTags()
                 .contains(tag)) {
            Tag existingTag = tagRepository.findByName(tag.getName())
                                           .get();

            if (existingTag != null) {
                tag = existingTag; // Use the existing tag instead of creating a new one
            } else {
                tag = tagRepository.createTag(tag); // Ensure the tag is persisted
            }

            post.getTags()
                .add(tag);
            postRepository.updatePost(post);
        }

        return tagRepository.findByName(tag.getName()).get();
    }

    @Override
    public void deleteTagFromPost(Tag tag, Post post,User user) {
        ValidatorHelpers.roleAuthenticationValidator(user,new UserRole(UserRoleEnum.ADMIN),post, USER_IS_NOT_AUTHOR_OF_THE_POST_OR_ADMIN);

        post.getTags()
            .remove(tag);
        updatePost(post);
    }

    @Override
    public Tag updatePostTag(Tag tag, Post post, User user, Tag newTag) {
        ValidatorHelpers.roleAuthenticationValidator(user, new UserRole(
                UserRoleEnum.ADMIN), post, "The user is not admin or author.");

        newTag = tagRepository.createOrUpdateTag(newTag);
        post.getTags()
            .remove(tag);
        post.getTags()
            .add(newTag);
        updatePost(post);
        return newTag;

    }


}
