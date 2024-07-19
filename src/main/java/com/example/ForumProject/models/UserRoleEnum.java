package com.example.ForumProject.models;

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
