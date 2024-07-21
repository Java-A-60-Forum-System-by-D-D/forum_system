package com.example.ForumProject.Services;

import com.example.ForumProject.models.User;

public interface AdminService {
    User grantAdminRights(int userId);

    User revokeAdminRights(int userId);

    com.example.ForumProject.repositories.UserRepository getUserRepository();

    com.example.ForumProject.repositories.UserRoleRepository getUserRoleRepository();
}
