package com.example.ForumProject.models.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Data
public class LoggInUserDTO {

    @Size(min = 4, max = 32,message = "Username's length is not valid, 4-32 symbols")
    private String username;

    private String password;

    public LoggInUserDTO() {
    }


}
