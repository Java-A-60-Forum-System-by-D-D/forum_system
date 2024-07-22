package com.example.ForumProject.services;

import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.models.User;
import com.example.ForumProject.models.UserRole;
import com.example.ForumProject.repositories.UserRepository;
import com.example.ForumProject.repositories.UserRoleRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;


    public AdminServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public User grantAdminRights(int userId) {
        User user = userRepository.getUserById(userId);
        UserRole userRole = userRoleRepository.getAdminRole();

        if(user.getUserRole().contains(userRole)){
            throw new AuthorizationException("User has admin privileges");
        }

        user.getUserRole()
            .add(userRole);

        return userRepository.updateUser(user);

    }

    @Override
    public User revokeAdminRights(int userId) {
        User user = userRepository.getUserById(userId);
        UserRole userRole = userRoleRepository.getAdminRole();

        if(!user.getUserRole().contains(userRole)){
            throw new AuthorizationException("User doesnt have admin privileges");
        }

        user.getUserRole()
            .remove(userRole);

        return userRepository.updateUser(user);

    }

    @Override
    public User grantModeratorRights(int userId) {
        User user = userRepository.getUserById(userId);
        UserRole userRole = userRoleRepository.getModeratorRole();

        if(user.getUserRole().contains(userRole)){
            throw new AuthorizationException("User has moderator privileges");
        }

        user.getUserRole()
            .add(userRole);

        return userRepository.updateUser(user);
    }

    @Override
    public User revokeModeratorRights(int userId) {
        User user = userRepository.getUserById(userId);
        UserRole userRole = userRoleRepository.getModeratorRole();

        if(!user.getUserRole().contains(userRole)){
            throw new AuthorizationException("User doesnt have moderator privileges");
        }

        user.getUserRole()
            .remove(userRole);

        return userRepository.updateUser(user);
    }



    @Override
    public User blockUser(int userId) {

        User user = userRepository.getUserById(userId);
        if(user.isBlocked()){
            throw new AuthorizationException("User is already blocked");
        }

        user.setBlocked(true);

        return userRepository.updateUser(user);

    }

    @Override
    public User unblockUser(int userId) {

        User user = userRepository.getUserById(userId);
        if(!user.isBlocked()){
            throw new AuthorizationException("User is not blocked");
        }

        user.setBlocked(false);

        return userRepository.updateUser(user);

    }


}
