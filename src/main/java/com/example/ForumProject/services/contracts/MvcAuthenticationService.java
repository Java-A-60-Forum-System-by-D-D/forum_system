package com.example.ForumProject.services.contracts;

import com.example.ForumProject.models.dto.LoggInUserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MvcAuthenticationService extends AuthenticationService {


    LoggInUserDTO loginUser(String username, String password, HttpServletRequest request, HttpServletResponse response);

    void logoutUser(HttpServletRequest request, HttpServletResponse response);
}
