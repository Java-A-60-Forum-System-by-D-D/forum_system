package com.example.ForumProject.services.contracts;

import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;

import java.util.List;

public interface PostService {
    List<Post> getPosts(User user);
    Post getPostById(int id);
    Post updatePost(Post post, User user,Post existingPost);
    Post createPost(Post post);
    void deletePost(int id, User user);
    List<Post> getPostsByUser(int id);
}
