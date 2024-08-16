package com.example.ForumProject.models.filterOptions;

import lombok.Getter;

import java.util.Optional;
@Getter
public class FilterOptionsUsers {
    private final Optional<String> username;
    private final Optional<String> firstName;
    private final Optional<String> lastName;
    private final Optional<String> role;
    private final Optional<Boolean> isBlocked;

    public FilterOptionsUsers(String username, String firstName, String lastName, String role, Boolean isBlocked) {
        this.username = Optional.ofNullable(username);
        this.firstName = Optional.ofNullable(firstName);
        this.lastName = Optional.ofNullable(lastName);
        this.role = Optional.ofNullable(role);
        this.isBlocked = Optional.ofNullable(isBlocked);
    }
}
