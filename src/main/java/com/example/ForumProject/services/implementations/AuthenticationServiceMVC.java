package com.example.ForumProject.services.implementations;

import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.models.dto.LoggInUserDTO;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.repositories.contracts.UserRepository;
import com.example.ForumProject.services.contracts.AuthenticationService;
import com.example.ForumProject.services.contracts.MvcAuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
@Qualifier("mvcAuthenticationService")
public class AuthenticationServiceMVC implements MvcAuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final ForumServiceDetails forumServiceDetails;

    @Autowired
    public AuthenticationServiceMVC(PasswordEncoder passwordEncoder,
                                    @Qualifier("mvcAuthenticationManager") AuthenticationManager authenticationManager,
                                    EmailService emailService,
                                    UserRepository userRepository,
                                    ForumServiceDetails forumServiceDetails) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.forumServiceDetails = forumServiceDetails;
    }


    @Override
    public User createUser(User user) {
        emailService.sendRegistrationEmail(user.getEmail());
        return userRepository.createUser(user);
    }


//    @Override
//    public LoggInUserDTO loginUser(String username, String password, HttpServletRequest request, HttpServletResponse response) {
//        User user = userRepository.getUserByUsername(username)
//                                  .get(0);
//        try {
//            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            Cookie sessionCookie = new Cookie("SESSIONID", WebUtils.getSessionId(request));
//            sessionCookie.setHttpOnly(true);
//            sessionCookie.setSecure(true);
//            sessionCookie.setPath("/**");
//            response.addCookie(sessionCookie);
//            LoggInUserDTO loggInUserDTO = new LoggInUserDTO();
//            loggInUserDTO.setUsername(user.getUsername());
//            loggInUserDTO.setPassword(user.getPassword());
//
//            return loggInUserDTO;
//        } catch (AuthenticationException e) {
//            throw new AuthorizationException("Invalid username or password");
//        }
//
//    }


    @Override
    public LoggInUserDTO loginUser(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        User user = userRepository.getUserByUsername(username)
                                  .get(0);
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            Cookie sessionCookie = new Cookie("SESSIONID", WebUtils.getSessionId(request));
            sessionCookie.setHttpOnly(true);
            sessionCookie.setSecure(true);
            sessionCookie.setPath("/**");
            response.addCookie(sessionCookie);
            LoggInUserDTO loggInUserDTO = new LoggInUserDTO();
            loggInUserDTO.setUsername(user.getUsername());
            loggInUserDTO.setPassword(user.getPassword());

            return loggInUserDTO;
        } catch (AuthenticationException e) {
            throw new AuthorizationException("Invalid username or password");
        }
    }

    @Override
    public void logoutUser(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie sessionCookie = new Cookie("SESSIONID", null);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setSecure(true);
        sessionCookie.setPath("/**");
        sessionCookie.setMaxAge(0);
        Authentication auth = SecurityContextHolder.getContext()
                                                   .getAuthentication();
        if (auth != null) {
            SecurityContextHolder.getContext()
                                 .setAuthentication(null);
        }

    }
}

