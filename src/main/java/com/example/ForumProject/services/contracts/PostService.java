package com.example.ForumProject.services.contracts;

import com.example.ForumProject.models.dto.PostSummaryDTO;
import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.models.persistentClasses.Like;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.Tag;
import com.example.ForumProject.models.persistentClasses.User;

import java.util.List;

public interface PostService {
    List<PostSummaryDTO> getPosts(User user, FilterOptionsPosts filterOptionsPosts);

    PostSummaryDTO getPostSummaryByPostId(int id);
    Post getPostByPostId(int id);

    List<PostSummaryDTO> get10MostCommentedPosts();
    List<PostSummaryDTO>get10MostRecentlyAddedPosts();

    PostSummaryDTO updatePost(Post post);

    PostSummaryDTO updatePost(Post post, User user, Post existingPost);

    PostSummaryDTO createPost(Post post, User user);

    void deletePost(int id, User user);

    List<PostSummaryDTO> getPostsByUser(int id);

    Tag createTag(Tag tag, Post post, User user);

    Like addLike(Like like, Post post);

    Like deleteLike(Like like, Post post);

    Tag updatePostTag(Tag tag, Post post, User user, Tag newTag);

    List<PostSummaryDTO> findPostsByTagId(int id);

    void deleteTagFromPost(Tag tag, Post post,User user);

    Tag addTagToPost(Tag tag, Post post);
}
