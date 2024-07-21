package com.example.ForumProject.repositories;

import com.example.ForumProject.models.Post;
import com.example.ForumProject.models.User;

import java.util.List;

public interface PostRepository {
    List<Post> getPosts();
    Post getPostById(int id);
    Post updatePost(Post post);
    Post createPost(Post post);
    void deletePost(int id);
    List<Post> getPostsByUser(int id);

}
