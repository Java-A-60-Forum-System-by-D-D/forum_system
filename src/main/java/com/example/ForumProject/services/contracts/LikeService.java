package com.example.ForumProject.services.contracts;

import com.example.ForumProject.models.persistentClasses.Like;

public interface LikeService {
    Like likePost(int userId, int postId);

    void unlikePost(int userId, int postId);

    Integer getLikesCount(int postId);
}
