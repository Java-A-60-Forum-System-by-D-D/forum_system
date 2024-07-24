package com.example.ForumProject.services.contracts;


import com.example.ForumProject.models.persistentClasses.Tag;
import com.example.ForumProject.models.persistentClasses.User;

import java.util.List;
import java.util.Optional;

public interface TagService {
    Optional<Tag> findByName(String name);

    void deleteTag(Tag tag);

    List<Tag> findTagsByPostId(int postId);

    List<Tag> findAllTags();

    Tag findById(int tagId);

    Tag updateTag(Tag tag, User user, Tag newTag);

    Tag createTag(Tag tag, User user);
}
