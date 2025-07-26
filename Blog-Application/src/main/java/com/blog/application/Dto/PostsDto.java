package com.blog.application.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostsDto {

    private String id;

    @NotBlank(message = "Title must not be blank")
    @Size(min = 6, message = "Content must be at least 5 characters")
    private String title;
    @NotBlank(message = "Content must not be blank")
    @Size(min = 5, message = "Content must be at least 5 characters")
    private String content;
    private String author;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private List<CommentDto> comments;
}
