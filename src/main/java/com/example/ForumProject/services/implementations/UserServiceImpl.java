package com.example.ForumProject.services.implementations;


import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.dto.UserSummaryDTO;
import com.example.ForumProject.models.filterOptions.FilterOptionsUsersPosts;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.services.contracts.UserService;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.repositories.contracts.UserRepository;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class UserServiceImpl implements UserService{


    private UserRepository userRepository;

//
//    @Override
//
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.getUserByUsername(username)
//                                  .stream()
//                                  .findFirst()
//                                  .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//
//        return user;
//    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public List<User> getUsers() {

        List<User> users = userRepository.getUsers();
        users.forEach(user -> Hibernate.initialize(user.getPosts()));
        return users;
    }



    @Override
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }


    @Override
    public User getUserByUsername(String username) {
        if (userRepository.getUserByUsername(username)
                          .isEmpty()) {
            throw new EntityNotFoundException("User", "username", username);
        }


        return userRepository.getUserByUsername(username)
                             .get(0);

    }

    @Override
    public User getUserByFirstName(String firstName) {


        return userRepository.getUserByFirstName(firstName);
    }

    @Override
    public User getUserByEmail(String email) {
        if (userRepository.getUserByEmail(email)
                          .isEmpty()) {
            throw new EntityNotFoundException("User", "email", email);
        }

        return userRepository.getUserByEmail(email)
                             .get(0);
    }


    @Override
    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }


    @Override
    public List<Post> getPostsByUser(User user, FilterOptionsUsersPosts filterOptionsUsersPosts) {
        return userRepository.getUsersPosts(user, filterOptionsUsersPosts);
    }


}
