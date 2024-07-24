package com.example.ForumProject.models.dto;

import com.example.ForumProject.utility.validation.UniqueEmail;
import com.example.ForumProject.utility.validation.UniqueUsername;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Optional;


@Data
@NoArgsConstructor
@EqualsAndHashCode
public class UserDTO {
    @Schema(description = "Username of the user. Must be unique.",
            example = "john_doe")
    @NotNull(message = "Field cannot be null")
    @UniqueUsername
    private String username;

    @Schema(description = "First name of the user. Must be between 4 and 32 characters.",
            example = "John")
    @NotNull(message = "Field cannot be null")
    @Size(min = 4, max = 32, message = "First name must be between 4 and 32 symbols")
    private String firstName;

    @Schema(description = "Last name of the user. Must be between 4 and 32 characters.",
            example = "Doe")
    @NotNull(message = "Field cannot be null")
    @Size(min = 4, max = 32, message = "Last name must be between 4 and 32 symbols")
    private String lastName;

    @Schema(description = "Password for the user account. It is recommended to have a strong password.",
            example = "P@ssw0rd123")
    @NotNull(message = "Field cannot be null")
    private String password;

    @Schema(description = "Email address of the user. Must be unique and a valid email format.",
            example = "john.doe@example.com")
    @NotNull(message = "Field cannot be null")
    @Email
    @UniqueEmail
    private String email;

    @Schema(description = "URL of the user's profile photo. Optional field.",
            example = "https://example.com/profile.jpg")
    private Optional<String> photoURL;

    @Schema(description = "Phone number of the user. Should be in a valid format for Bulgarian phone numbers.",
            example = "+359 888 888 888")
    @Pattern(regexp = "^(((\\+|00)359[- ]?)|(0))([89][- ]?[789]([- ]?\\d){7})$")
    private String phoneNumber;


}
