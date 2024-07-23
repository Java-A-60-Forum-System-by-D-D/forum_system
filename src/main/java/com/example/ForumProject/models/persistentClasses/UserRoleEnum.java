package com.example.ForumProject.models.persistentClasses;

import io.swagger.v3.oas.annotations.Hidden;

public enum UserRoleEnum {

    USER,
    ADMIN,
    MODERATOR;

    @Override
    public String toString() {
        switch (this) {
            case USER:
                return "User";
            case ADMIN:
                return "Admin";
            default:
                return "Moderator";
        }
    }
}
