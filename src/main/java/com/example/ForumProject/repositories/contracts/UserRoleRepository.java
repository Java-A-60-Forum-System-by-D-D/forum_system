package com.example.ForumProject.repositories.contracts;

import com.example.ForumProject.models.persistentClasses.UserRole;

public interface UserRoleRepository {
    UserRole getUserRole();

    UserRole getAdminRole();

    UserRole getModeratorRole();
}
