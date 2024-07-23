package com.example.ForumProject.models.filterOptions;

import java.util.Optional;

public class FilterOptionsComments {
    private final Optional<String> sortBy;
    private final Optional<String> sortOrder;

    public FilterOptionsComments(String sortBy, String sortOrder) {
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);

    }
}
