package com.example.ForumProject.Services;

import com.example.ForumProject.models.Comment;
import com.example.ForumProject.models.Post;
import com.example.ForumProject.models.User;

import java.util.List;

public interface PostService {
    List<Post> getPosts();
    Post getPostById(int id);
    Post updatePost(Post post, User user,Post existingPost);
    Post createPost(Post post);
    void deletePost(int id, User user);
    List<Post> getPostsByUser(int id);
}
