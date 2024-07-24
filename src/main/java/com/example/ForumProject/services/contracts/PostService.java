package com.example.ForumProject.services.contracts;

import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.models.persistentClasses.Like;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.Tag;
import com.example.ForumProject.models.persistentClasses.User;

import java.util.List;

public interface PostService {
    List<Post> getPosts(User user, FilterOptionsPosts filterOptionsPosts);

    Post getPostById(int id);

    List<Post> get10MostCommentedPosts();
    List<Post >get10MostRecentlyAddedPosts();

    Post updatePost(Post post);

    Post updatePost(Post post, User user, Post existingPost);

    Post createPost(Post post, User user);

    void deletePost(int id, User user);

    List<Post> getPostsByUser(int id);

    Tag createTag(Tag tag, Post post, User user);

    Like addLike(Like like, Post post);

    Like deleteLike(Like like, Post post);

    Tag updatePostTag(Tag tag, Post post, User user, Tag newTag);

    List<Post> findPostsByTagId(int id);

    void deleteTagFromPost(Tag tag, Post post);

    Tag addTagToPost(Tag tag, Post post);
}
