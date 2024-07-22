package com.example.ForumProject.controllers;

import com.example.ForumProject.services.AdminService;
import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;


    @Autowired
    public AdminController(AdminService adminService) {

        this.adminService = adminService;
    }

    @PostMapping("/admin-privileges/{user_id}")
    public User grantAdminRights(@PathVariable int user_id) {

        try {
            return adminService.grantAdminRights(user_id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

    }

    @PostMapping("/moderator-privileges/{user_id}")
    public User grantModeratorRights(@PathVariable int user_id){
        try {
            return adminService.grantModeratorRights(user_id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

    }

    @DeleteMapping("/admin-privileges/{user_id}")
    public User revokeAdminRights(@PathVariable int user_id) {

        try {
            return adminService.revokeAdminRights(user_id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

    }

    @DeleteMapping("/moderator-privileges/{user_id}")
    public User revokeModeratorRights(@PathVariable int user_id) {

        try {
            return adminService.revokeModeratorRights(user_id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

    }


    @PostMapping("/block/{user_id}")
    public User blockUser(@PathVariable int user_id){

        try {
            return adminService.blockUser(user_id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }


    }

    @DeleteMapping("/block/{user_id}")
    public User unblockUser(@PathVariable int user_id){

        try {
            return adminService.unblockUser(user_id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }


    }



}
