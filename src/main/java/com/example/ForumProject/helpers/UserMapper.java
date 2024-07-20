package com.example.ForumProject.helpers;

import com.example.ForumProject.Services.UserService;
import com.example.ForumProject.models.User;
import com.example.ForumProject.models.UserRole;
import com.example.ForumProject.models.UserRoleEnum;
import com.example.ForumProject.models.dto.UserDTO;
import com.example.ForumProject.repositories.UserRoleRepositoryImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepositoryImpl userRoleRepository;

    @Autowired
    public UserMapper(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleRepositoryImpl userRoleRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    public User createFromDto(int id, UserDTO userDTO) {
        User user = createFromDto(userDTO);
        user.setId(id);
        return user;
    }


    public User createFromDto(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRoleRepository.getUserRole());
        user.setUserRole(userRoles);


        return user;
    }


}
