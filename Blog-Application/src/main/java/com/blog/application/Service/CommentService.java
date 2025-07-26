package com.blog.application.Service;

import com.blog.application.Dto.CommentDto;
import org.springframework.stereotype.Component;

@Component
public interface CommentService {
    CommentDto createComment(CommentDto commentDto);
}
