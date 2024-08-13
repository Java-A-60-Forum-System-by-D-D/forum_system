package com.example.ForumProject.services.implementations;

import com.example.ForumProject.models.dto.PostSummaryDTO;
import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.models.persistentClasses.*;
import com.example.ForumProject.repositories.contracts.TagRepository;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.repositories.contracts.PostRepository;
import com.example.ForumProject.services.contracts.UserService;
import com.example.ForumProject.utility.ValidatorHelpers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {
    public static final String INVALID_DELETE_COMMAND = "You have no rights to delete this post";
    public static final String INVALID_UPDATE_COMMAND = "You have no rights to update this post";
    public static final String INVALID_GET_ALL_POSTS_COMMAND = "You must be logged in to see all posts";
    public static final String USER_IS_NOT_AUTHOR_OF_THE_POST_OR_ADMIN = "User is not author of the post or admin";
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;


    @Autowired
    public PostServiceImpl(PostRepository postRepository, TagRepository tagRepository, UserService userService, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<PostSummaryDTO> getPosts(User user, FilterOptionsPosts filterOptionsPosts) {

        ValidatorHelpers.roleAuthenticationValidator(user, new UserRole(UserRoleEnum.USER), INVALID_GET_ALL_POSTS_COMMAND);


        return postRepository.getPosts(filterOptionsPosts)
                             .stream()
                             .map(this::postMapper)
                             .collect(Collectors.toList());
    }

    @Override
    public PostSummaryDTO getPostSummaryByPostId(int id) {

        PostSummaryDTO postSummaryDTO = postMapper(postRepository.getPostById(id));
        return postSummaryDTO;
    }

    @Override
    public Post getPostByPostId(int id) {
        return postRepository.getPostById(id);
    }


    @Override
    public List<PostSummaryDTO> get10MostCommentedPosts() {
        return postRepository.get10MostCommentedPosts()
                             .stream()
                             .map(this::postMapper)
                             .collect(Collectors.toList());
    }


    @Override
    public List<PostSummaryDTO> get10MostRecentlyAddedPosts() {
        return postRepository.get10MostRecentlyAddedPosts()
                             .stream()
                             .map(this::postMapper)
                             .collect(Collectors.toList());
    }


    @Override
    public PostSummaryDTO createPost(Post post, User user) {
        postRepository.checkIfPostWithTitleExistsForUser(post, user);

        user.getPosts()
            .add(post);

        userService.updateUser(user);

        Post createdPost = postRepository.createPost(post);
        return postMapper(createdPost);
    }

    @Override
    public PostSummaryDTO updatePost(Post post) {

        PostSummaryDTO postSummaryDTO = postMapper(postRepository.updatePost(post));
        postRepository.updatePost(post);
        return postSummaryDTO;
    }

    @Override
    public PostSummaryDTO updatePost(Post post, User user, Post existingPost) {


        ValidatorHelpers.roleAuthenticationValidator(user, new UserRole(UserRoleEnum.ADMIN), existingPost, INVALID_UPDATE_COMMAND);
        postRepository.checkIfPostWithTitleExistsForUser(post, user);

        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());

        PostSummaryDTO postSummaryDTO = postMapper(postRepository.updatePost(existingPost));
        postRepository.updatePost(post);
        return postSummaryDTO;
    }


    @Override
    public void deletePost(int id, User user) {


        ValidatorHelpers.roleAuthenticationValidator(user, new UserRole(UserRoleEnum.ADMIN), this.getPostSummaryByPostId(id), INVALID_DELETE_COMMAND);
        user.getPosts()
            .remove(this.getPostSummaryByPostId(id));
        userService.updateUser(user);

        postRepository.deletePost(id);
    }

    @Override
    public List<PostSummaryDTO> getPostsByUser(int id) {

        return postRepository.getPostsByUser(id)
                             .stream()
                             .map(this::postMapper)
                             .collect(Collectors.toList());
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
    public List<PostSummaryDTO> findPostsByTagId(int id) {
        return postRepository.getPostsByTagId(id)
                             .stream()
                             .map(this::postMapper)
                             .collect(Collectors.toList());

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

        return tagRepository.findByName(tag.getName())
                            .get();
    }

    @Override
    public void deleteTagFromPost(Tag tag, Post post, User user) {
        ValidatorHelpers.roleAuthenticationValidator(user, new UserRole(UserRoleEnum.ADMIN), post, USER_IS_NOT_AUTHOR_OF_THE_POST_OR_ADMIN);

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


    private PostSummaryDTO postMapper(Post post) {
        PostSummaryDTO postSummaryDTO = modelMapper.map(post, PostSummaryDTO.class);
        User user = post.getUser();

        postSummaryDTO.setUsername(user.getUsername());
        postSummaryDTO.setComments(post.getComments());
        postSummaryDTO.setTags(post.getTags());
        postSummaryDTO.setCategory(post.getCategory()
                                       .getCategoryName());

        return postSummaryDTO;
    }


}
