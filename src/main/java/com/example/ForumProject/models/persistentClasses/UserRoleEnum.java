package com.example.ForumProject.models.persistentClasses;

import com.example.ForumProject.exceptions.EntityNotFoundException;
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
    public static UserRoleEnum fromString(String role) {
        for (UserRoleEnum r : UserRoleEnum.values()) {
            if (r.toString().equalsIgnoreCase(role)) {
                return r;
            }
        }
        throw new EntityNotFoundException("No enum constant " + UserRoleEnum.class.getCanonicalName() + "." + role);
    }
}
