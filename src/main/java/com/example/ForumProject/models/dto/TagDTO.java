package com.example.ForumProject.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TagDTO {
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 symbols" )
    @NotNull
    private String name;

    public TagDTO() {
    }
}
