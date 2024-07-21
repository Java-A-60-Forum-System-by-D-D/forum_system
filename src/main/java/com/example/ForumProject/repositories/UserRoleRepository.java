package com.example.ForumProject.repositories;

import com.example.ForumProject.models.UserRole;

public interface UserRoleRepository {
    UserRole getUserRole();

    UserRole getAdminRole();

    UserRole getModeratorRole();
}
