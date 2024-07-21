package com.example.ForumProject.models.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDTO {
    @Size(min = 32, max = 8192, message = "Content must be between 32 and 8192 symbols")
        private String content;

}
