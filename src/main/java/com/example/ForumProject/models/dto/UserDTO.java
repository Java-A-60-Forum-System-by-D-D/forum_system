package com.example.ForumProject.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;


@NoArgsConstructor
@Data
public class UserDTO {

    @NotNull
    private String username;

    @NotNull(message = "Field cannot be null")
    @Size(min = 4, max = 32, message = "First name must be between 4 and 32 symbols")
    private String firstName;

    @NotNull(message = "Field cannot be null")
    @Size(min = 4, max = 32, message = "Last name must be between 4 and 32 symbols")
    private String lastName;

    @NotNull(message = "Field cannot be null")
    private String password;

    @NotNull(message = "Field cannot be null")
    @Email
    private String email;

//    @Size(min = 10, max = 10, message = "Phone number must be valid length")
//    private String phoneNumber;




}
