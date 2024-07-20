package com.example.ForumProject.Services;


import com.example.ForumProject.helpers.LoggedUser;
import com.example.ForumProject.models.User;
import com.example.ForumProject.models.dto.LoggInUserDTO;
import com.example.ForumProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private LoggedUser loggedUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loggedUser = loggedUser;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User getUserByUsername(String username) {

        return userRepository.getUserByUsername(username)
                             .get(0);

    }

    @Override
    public User getUserByFirstName(String firstName) {
        return userRepository.getUserByFirstName(firstName);
    }

    @Override
    public User createUser(User user) {
        return userRepository.createUser(user);

    }

    @Override
    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    public boolean verifyPassword(User user, String rawPassword) {
        return false;
    }


    @Override
    public boolean login(LoggInUserDTO loggInUserDTO) {

        User user = getUserByUsername(loggInUserDTO.getUsername());


        if (user != null && passwordEncoder.matches(user.getPassword(), loggInUserDTO.getPassword())) {
            loggedUser.setUsername(user.getUsername());
            loggedUser.setLogged(true);

            return true;
        }

        return false;
    }

    @Override
    public void logout() {
        loggedUser.setUsername(null);
        loggedUser.setLogged(false);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            User user = getUserByUsername(username);


        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }


}
