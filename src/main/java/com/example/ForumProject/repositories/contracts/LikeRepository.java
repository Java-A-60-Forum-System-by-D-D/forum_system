package com.example.ForumProject.repositories.contracts;

import com.example.ForumProject.models.persistentClasses.Like;

import java.util.List;
import java.util.Optional;

public interface LikeRepository {
    Like save(Like like);
    void delete(Like like);
    Optional<Like> findByPostIdAndUserId(int postId, int userId);
    List<Like> findByPostId(int postId);
    Integer countByPostId(int postId);


}
