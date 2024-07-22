package com.example.ForumProject.models.filterOptions;

import lombok.Getter;

import java.util.Optional;

@Getter
public class FilterOptionsPosts {


    private final Optional<String> title;
    private final Optional<String> content;
    private final Optional<String> user;
    private final Optional<String> tag;

    public FilterOptionsPosts(String title, String content, String user, String tag) {
        this.title = Optional.ofNullable(title);
        this.content = Optional.ofNullable(content);
        this.user = Optional.ofNullable(user);
        this.tag = Optional.ofNullable(tag);
    }
}
