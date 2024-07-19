package com.example.ForumProject.helpers;

import com.example.ForumProject.Services.UserService;
import com.example.ForumProject.models.User;
import com.example.ForumProject.models.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User createFromDto(int id, UserDTO userDTO) {
        User user = createFromDto(userDTO);
        user.setId(id);
        return user;
    }


    public User createFromDto(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return user;
    }


}
