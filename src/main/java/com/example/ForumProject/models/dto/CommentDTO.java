package com.example.ForumProject.models.dto;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Optional;

@Data

public class CommentDTO {
    @Size(min = 32, max = 8192, message = "Content must be between 32 and 8192 symbols")
    @NotNull(message = "Comments cant be null")
    private String content;

    private Optional<Integer> parentCommentId;


}
