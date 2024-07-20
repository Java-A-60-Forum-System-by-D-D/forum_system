package com.example.ForumProject.models.dto;

import com.example.ForumProject.validation.UniqueEmail;
import com.example.ForumProject.validation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;



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

    public @NotNull String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    public @NotNull(message = "Field cannot be null") @Size(min = 4, max = 32, message = "First name must be between 4 and 32 symbols") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull(message = "Field cannot be null") @Size(min = 4, max = 32, message = "First name must be between 4 and 32 symbols") String firstName) {
        this.firstName = firstName;
    }

    public @NotNull(message = "Field cannot be null") @Size(min = 4, max = 32, message = "Last name must be between 4 and 32 symbols") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull(message = "Field cannot be null") @Size(min = 4, max = 32, message = "Last name must be between 4 and 32 symbols") String lastName) {
        this.lastName = lastName;
    }

    public @NotNull(message = "Field cannot be null") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "Field cannot be null") String password) {
        this.password = password;
    }

    public @NotNull(message = "Field cannot be null") @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "Field cannot be null") @Email String email) {
        this.email = email;
    }
}
