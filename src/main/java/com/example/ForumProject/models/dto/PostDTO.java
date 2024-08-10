package com.example.ForumProject.models.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class PostDTO {

    @Schema(
            description = "Title of the post. Must be between 16 and 64 characters long.",
            example = "The Importance of Clean Code"
    )
    @Size(min = 16, max = 64, message = "Title must be between 16 and 64 symbols")
    private String title;
    @Schema(
            description = "Content of the post. Must be between 32 and 8192 characters long.",
            example = "Clean code is a term used to describe code that is easy to read, understand, and maintain. In this article, we will explore the principles and practices of writing clean code..."
    )
    @Size(min = 32, max = 8192, message = "Content must be between 32 and 8192 symbols")
    private String content;


    @NotNull(message = "Category id cannot be null")
    private Integer categoryNumber;

    public PostDTO() {
    }


}

