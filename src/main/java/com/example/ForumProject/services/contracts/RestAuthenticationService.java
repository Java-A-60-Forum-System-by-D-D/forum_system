package com.example.ForumProject.services.contracts;

import com.example.ForumProject.models.dto.LoggInUserDTO;

public interface RestAuthenticationService extends AuthenticationService{

    LoggInUserDTO loginUser(String username, String password);
}
