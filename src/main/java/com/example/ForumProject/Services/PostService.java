package com.example.ForumProject.Services;

import com.example.ForumProject.models.Comment;
import com.example.ForumProject.models.Post;

import java.util.List;

public interface PostService {
    List<Post> getPosts();
    Post getPostById(int id);
    Post updatePost(Post post);
    Post createPost(Post post);
    void deletePost(int id);
    List<Post> getPostsByUser(int id);
}
