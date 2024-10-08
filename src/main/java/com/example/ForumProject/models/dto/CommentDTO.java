package com.example.ForumProject.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDTO {

    @Schema(description = "Content of the comment. Must be between 32 and 8192 characters.", example = "This is a comment with detailed content that provides feedback on the post.")
    @Size(min = 32, max = 8192, message = "Content must be between 32 and 8192 symbols")
    @NotNull(message = "Comments cant be null")
    private String content;


    private int commentParent;


}

