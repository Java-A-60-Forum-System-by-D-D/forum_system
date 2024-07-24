package com.example.ForumProject.services.contracts;

import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.repositories.contracts.UserRepository;
import com.example.ForumProject.repositories.contracts.UserRoleRepository;

public interface AdminService {
    User grantAdminRights(int userId);

    User revokeAdminRights(int userId);

    User grantModeratorRights(int userId);

    User revokeModeratorRights(int userId);

    User blockUser(int userId);

    User unblockUser(int userId);


}