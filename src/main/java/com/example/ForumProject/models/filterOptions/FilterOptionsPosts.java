package com.example.ForumProject.models.filterOptions;

import lombok.Getter;

import java.util.Optional;

@Getter
public class FilterOptionsPosts {


    private final Optional<String> title;
    private final Optional<String> content;
    private final Optional<Integer> userId;
    private final Optional<Integer> tagId;

    public FilterOptionsPosts(String title, String content, Integer userId, Integer tagId) {
        this.title = Optional.ofNullable(title);
        this.content = Optional.ofNullable(content);
        this.userId = Optional.ofNullable(userId);
        this.tagId = Optional.ofNullable(tagId);
    }
}
