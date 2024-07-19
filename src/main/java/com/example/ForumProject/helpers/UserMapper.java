package com.example.ForumProject.helpers;

import com.example.ForumProject.models.User;
import com.example.ForumProject.models.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public User fromDto(UserDTO userDTO) {

        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        return user;

    }


}
