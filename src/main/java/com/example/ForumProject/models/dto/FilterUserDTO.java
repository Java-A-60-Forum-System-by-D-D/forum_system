package com.example.ForumProject.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilterUserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String role;
    private boolean isBlocked;
}
