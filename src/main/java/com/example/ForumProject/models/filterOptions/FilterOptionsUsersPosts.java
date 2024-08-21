package com.example.ForumProject.models.filterOptions;

import lombok.Getter;

import java.util.Optional;

@Getter
public class FilterOptionsUsersPosts {

    private final Optional<String> title;
    private final Optional<String> content;
    private final Optional<Integer> tagId;
    private final Optional<String> sortBy;
    private final Optional<String> sortOrder;

    public FilterOptionsUsersPosts(String title, String content, Integer tagId, String sortBy, String sortOrder) {
        this.title = Optional.ofNullable(title);
        this.content = Optional.ofNullable(content);
        this.tagId = Optional.ofNullable(tagId);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }


    public FilterOptionsUsersPosts() {
        this.title = Optional.empty();
        this.content = Optional.empty();
        this.tagId = Optional.empty();
        this.sortBy = Optional.empty();
        this.sortOrder = Optional.empty();
    }
}
