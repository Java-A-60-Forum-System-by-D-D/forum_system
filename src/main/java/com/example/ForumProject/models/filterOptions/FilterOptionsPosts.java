package com.example.ForumProject.models.filterOptions;

import lombok.Getter;

import java.util.Optional;

@Getter
public class FilterOptionsPosts {


    private Optional<String> title;
    private Optional<String> content;
    private Optional<Integer> userId;
    private Optional<Integer> tagId;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public FilterOptionsPosts(String title, String content, Integer userId, Integer tagId, String sortBy, String sortOrder) {
        this.title = Optional.ofNullable(title);
        this.content = Optional.ofNullable(content);
        this.userId = Optional.ofNullable(userId);
        this.tagId = Optional.ofNullable(tagId);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }

    public FilterOptionsPosts() {
        this.title = Optional.empty();
        this.content = Optional.empty();
        this.userId = Optional.empty();
        this.tagId = Optional.empty();
        this.sortBy = Optional.empty();
        this.sortOrder = Optional.empty();
    }
}
