package com.example.ForumProject.models.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class LoggInUserDTO {

    @Size(min = 4, max = 32, message = "Username's length is not valid, 4-32 symbols")
    private String username;

    private String password;

    private String token;

    public LoggInUserDTO() {
    }

    public LoggInUserDTO(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }
}
