//package com.example.ForumProject.models;
//
//import com.example.ForumProject.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
//
//    private final UserRepository userRepository;
//
//
//    @Autowired
//    public UserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.getUserByUsername(username);
//        return org.springframework.security.core.userdetails.User
//                .withUsername(user.getUsername())
//                .password(user.getPassword())
//                .roles("USER")
//                .build();
//    }
//}
