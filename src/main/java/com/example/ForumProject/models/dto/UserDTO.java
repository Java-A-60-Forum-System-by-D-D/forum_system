package com.example.ForumProject.models.dto;

import com.example.ForumProject.utility.validation.UniqueEmail;
import com.example.ForumProject.utility.validation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@NoArgsConstructor
@EqualsAndHashCode
public class UserDTO {

    @NotNull(message = "Field cannot be null")
    @UniqueUsername
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
    @UniqueEmail
    private String email;



    @Pattern(regexp = "^(((\\+|00)359[- ]?)|(0))([89][- ]?[789]([- ]?\\d){7})$")
    private String phoneNumber;

  
}
