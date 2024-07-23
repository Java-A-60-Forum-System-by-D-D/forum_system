package com.example.ForumProject.models.helpers;

import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.persistentClasses.UserRole;
import com.example.ForumProject.models.dto.UserDTO;
import com.example.ForumProject.repositories.contracts.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserMapper(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    public User createUserFromDto(int id, UserDTO userDTO) {
        User user = createUserFromDto(userDTO);
        user.setId(id);
        return user;
    }


    public User createUserFromDto(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRoleRepository.getUserRole());
        Optional<String> photoURL = userDTO.getPhotoURL();
        if(photoURL.isPresent()){
            user.setPhotoURL(photoURL.toString());
        }
        user.setUserRole(userRoles);


        return user;
    }




}
