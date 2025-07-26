package com.blog.application.Service.Impl;

import com.blog.application.Controller.PostsController;
import com.blog.application.Dto.CommentDto;
import com.blog.application.Entity.Comments;
import com.blog.application.Entity.Posts;
import com.blog.application.Repository.CommentRepository;
import com.blog.application.Repository.PostsRepository;
import com.blog.application.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);


    @Override
    public CommentDto createComment(CommentDto commentDto) {

        Posts post = postsRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + commentDto.getPostId()));

        Comments comment = Comments.builder()
                .id(UUID.randomUUID().toString())
                .post(post)
                .content(commentDto.getContent())
                .author(commentDto.getAuthor())
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();

        Comments saved = commentRepository.save(comment);


        return CommentDto.builder()
                .id(saved.getId())
                .postId(post.getId())
                .content(saved.getContent())
                .author(saved.getAuthor())
                .createdTime(saved.getCreatedTime())
                .updatedTime(saved.getUpdatedTime())
                .build();
    }




}

