package com.example.ForumProject.models.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data

public class LoggInUserDTO {

//    @Schema(description = "Username of the user. Must be between 4 and 32 characters long.", example = "john_doe")
    @Size(min = 4, max = 32, message = "Username's length is not valid, 4-32 symbols")
    private String username;
//    @Schema(description = "Password of the user. Used for authentication.", example = "password123")
    private String password;
//    @Schema(description = "JWT token received upon successful authentication. This token is used to authorize further requests.")
    private String token;

    public LoggInUserDTO() {
    }

    public LoggInUserDTO(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }
}
