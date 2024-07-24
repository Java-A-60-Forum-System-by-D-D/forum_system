package com.example.ForumProject.repositories.contracts;

import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.models.persistentClasses.Like;
import com.example.ForumProject.models.persistentClasses.Post;

import java.util.List;

public interface PostRepository {
    List<Post> getPosts(FilterOptionsPosts filterOptionsPosts);
    Post getPostById(int id);
    Post updatePost(Post post);
    Post createPost(Post post);
    void deletePost(int id);
    List<Post> getPostsByUser(int id);


    List<Post> getPostsByTagId(int id);
}
