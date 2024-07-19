package com.example.ForumProject.Services;


import com.example.ForumProject.models.User;
import com.example.ForumProject.repositories.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

    private UserRepositoryImpl userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepositoryImpl userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User getById(int id) {
        return userRepository.getById(id);
    }

    public User getByUsername(String username) {
        return userRepository.getByName(username);
    }

    public User getByFirstName(String firstName){
        return userRepository.getByFirstName(firstName);
    }

    public User createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.createUser(user);
        return user;
    }




    public boolean verifyPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }


}
