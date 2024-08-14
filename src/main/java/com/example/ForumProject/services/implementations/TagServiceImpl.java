package com.example.ForumProject.services.implementations;

import com.example.ForumProject.exceptions.EntityDuplicateException;
import com.example.ForumProject.models.persistentClasses.*;
import com.example.ForumProject.repositories.contracts.PostRepository;
import com.example.ForumProject.repositories.contracts.TagRepository;
import com.example.ForumProject.services.contracts.TagService;
import com.example.ForumProject.utility.ValidatorHelpers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    public static final String ONLY_ADMINS_CAN_DELETE_TAGS_FROM_THE_SYSTEM = "Only Admins can delete tags from the system";
    private final TagRepository tagRepository;
    private final PostRepository postRepository;


    public TagServiceImpl(TagRepository tagRepository, PostRepository postRepository) {
        this.tagRepository = tagRepository;
        this.postRepository = postRepository;
    }


    @Override
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }


    @Override
    public void deleteTag(Tag tag, User user) {
        ValidatorHelpers.roleAuthenticationValidator(user, new UserRole(UserRoleEnum.ADMIN), ONLY_ADMINS_CAN_DELETE_TAGS_FROM_THE_SYSTEM);

        List<Post> posts = postRepository.getPostsByTagId(tag.getId());
        for (Post post : posts) {
            post.getTags()
                .remove(tag);
            postRepository.updatePost(post);
        }
        tagRepository.deleteTag(tag);

    }

    @Override
    public List<Tag> findTagsByPostId(int postId) {
        return tagRepository.findTagsByPostId(postId);
    }

    @Override
    public List<Tag> findAllTags() {
        return tagRepository.findAllTags();
    }

    @Override
    public Tag findById(int tagId) {
        return tagRepository.findTagById(tagId);
    }

    @Override
    public Tag updateTag(Tag tag, User user, Tag newTag) {
        ValidatorHelpers.roleAuthenticationValidator(user, new UserRole(UserRoleEnum.ADMIN), "Only admins can update Tags.");
        newTag = tagRepository.createOrUpdateTag(newTag);
        tagRepository.deleteTag(tag);
        return newTag;
    }

    @Override
    public Tag createTag(Tag tag, User user) {
        Optional<Tag> existingTag = tagRepository.findByName(tag.getName());
        if (existingTag.isPresent()) {
            return existingTag.get();
        } else {
            return tagRepository.createTag(tag);
        }
    }

    @Override
    public Tag findOrCreateTagByName(String name) {
        return tagRepository.findByName(name).orElseGet(() -> {
            Tag tag = new Tag();
            tag.setName(name);
            return tagRepository.createTag(tag);
        });
    }
}
