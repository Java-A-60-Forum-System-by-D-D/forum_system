package com.example.ForumProject.Services;

import com.example.ForumProject.models.User;

public interface AdminService {
    User grantAdminRights(int userId);

    User revokeAdminRights(int userId);
    User grantModeratorRights(int userId);
    User revokeModeratorRights(int userId);
    User blockUser(int userId);
    User unblockUser(int userId);

    com.example.ForumProject.repositories.UserRepository getUserRepository();

    com.example.ForumProject.repositories.UserRoleRepository getUserRoleRepository();
}
