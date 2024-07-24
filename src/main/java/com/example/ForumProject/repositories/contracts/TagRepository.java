package com.example.ForumProject.repositories.contracts;

import com.example.ForumProject.models.persistentClasses.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    Optional<Tag> findByName(String name);
    Tag createTag(Tag tag);
    void deleteTag(Tag tag);
    List<Tag> findTagsByPostId(int postId);
    List<Tag> findAllTags();


    Tag findTagById(int tagId);

    Tag createOrUpdateTag(Tag tag);
}
