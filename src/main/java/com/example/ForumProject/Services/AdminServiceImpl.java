package com.example.ForumProject.Services;

import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.models.User;
import com.example.ForumProject.models.UserRole;
import com.example.ForumProject.models.UserRoleEnum;
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


}
