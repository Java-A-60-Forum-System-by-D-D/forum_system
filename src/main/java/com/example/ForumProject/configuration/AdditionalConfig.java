package com.example.ForumProject.configuration;

import com.example.ForumProject.models.helpers.StringToTagConverter;
import com.example.ForumProject.repositories.contracts.UserRepository;
import com.example.ForumProject.services.implementations.ForumServiceDetails;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;

@Configuration
public class AdditionalConfig {
    private final UserRepository userRepository;

    public AdditionalConfig(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public ForumServiceDetails forumServiceDetails() {
        return new ForumServiceDetails();
    }


}
