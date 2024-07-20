package com.example.ForumProject.models.dto;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class LoggInUserDTO {

    @Size(min = 4, max = 32,message = "Username's length is not valid, 4-32 symbols")
    private String username;

    private String password;

    public LoggInUserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
