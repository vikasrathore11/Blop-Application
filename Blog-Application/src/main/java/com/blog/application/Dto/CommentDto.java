package com.blog.application.Dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private String id;

    @NotBlank(message = "PostId must not be blank")
    private String  postId;
    @NotBlank(message = "Content must not be blank")
    @Size(min = 5, message = "Content must be at least 5 characters")
    private String content;

    @NotBlank(message = "Author Name must not be blank")
    private String author;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;


}
