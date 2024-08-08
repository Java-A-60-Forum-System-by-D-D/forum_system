package com.example.ForumProject.services.implementations;

import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.persistentClasses.UserRole;
import com.example.ForumProject.repositories.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
@Service
@Qualifier
public class ForumServiceDetails implements UserDetailsService {
    @Autowired
    private  UserRepository userRepository;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username)
                             .stream()
                             .filter(u -> u.getUsername()
                                           .equalsIgnoreCase(username))
                             .map(ForumServiceDetails::map)
                             .findFirst()
                             .orElseThrow(() -> new EntityNotFoundException("User", username));


    }


    private static UserDetails map(User forumDetailSerivce) {

        return org.springframework.security.core.userdetails.User
                .withUsername(forumDetailSerivce.getUsername())
                .password(forumDetailSerivce.getPassword())
                .authorities(forumDetailSerivce.getUserRole()
                                               .stream()
                                               .map(ForumServiceDetails::map)
                                               .toList())
                .build();
    }

    private static GrantedAuthority map(UserRole userRoleEntity) {
        return new SimpleGrantedAuthority(
                "ROLE_" + userRoleEntity.getRole()
                                        .name()
        );
    }


}
