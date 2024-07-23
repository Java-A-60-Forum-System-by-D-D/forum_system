package com.example.ForumProject.models.helpers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.persistentClasses.UserRole;
import com.example.ForumProject.models.dto.UserDTO;
import com.example.ForumProject.repositories.contracts.UserRoleRepository;
import com.example.ForumProject.services.contracts.CloudinaryImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final CloudinaryImageService cloudinaryImageService;

    @Autowired
    public UserMapper(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository, CloudinaryImageService cloudinaryImageService) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.cloudinaryImageService = cloudinaryImageService;

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
        if (photoURL.isPresent() && !photoURL.get().isEmpty()) {
            try {
                String uploadedUrl = cloudinaryImageService.uploadImageFromUrl(photoURL.get());
                user.setPhotoURL(uploadedUrl);
            } catch (IOException e) {
                throw new EntityNotFoundException();
            }
        }else{
            user.setPhotoURL("http://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png");
        }

        user.setUserRole(userRoles);


        return user;
    }




}
