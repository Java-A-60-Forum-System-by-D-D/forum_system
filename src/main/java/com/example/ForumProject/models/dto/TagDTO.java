package com.example.ForumProject.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class TagDTO {
    @Schema(
            description = "Name of the tag. Must be between 3 and 20 characters long.",
            example = "Technology"
    )
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 symbols" )
    @NotNull
    private String name;

    public TagDTO() {
    }
}
