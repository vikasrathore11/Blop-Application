package com.blog.application.Controller;

import com.blog.application.Dto.CommentDto;
import com.blog.application.Entity.Comments;
import com.blog.application.Entity.Posts;
import com.blog.application.Exception.UserNotFoundException;
import com.blog.application.Repository.CommentRepository;
import com.blog.application.Repository.PostsRepository;
import com.blog.application.Service.CommentService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")

public class CommentController {


    @Autowired
    private CommentService commentService;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDto commentDto) {

        CommentDto comment = commentService.createComment(commentDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);

    }


    @GetMapping("/readComment/{postId}")
    public ResponseEntity<?> readComment(@PathVariable String postId) {
        try {

            Posts post = postsRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post Not Found"));

            List<Comments> comments = post.getComments();

            List<CommentDto> commentDtos = comments.stream().map(comment -> {
                CommentDto dto = new CommentDto();
                dto.setId(comment.getId());
                dto.setContent(comment.getContent());
                dto.setAuthor(comment.getAuthor());
                dto.setCreatedTime(comment.getCreatedTime());
                dto.setUpdatedTime(comment.getUpdatedTime());
                dto.setPostId(comment.getPost().getId());
                return dto;
            }).collect(Collectors.toList());

            return new ResponseEntity<>(commentDtos, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid UUID format", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getSingleComment/{commentId}")
    public ResponseEntity<?> getSingleComment(@PathVariable String commentId) {
        try {

            Optional<Comments> comment = commentRepository.findById(commentId);


            CommentDto dto = new CommentDto();
            dto.setId(comment.get().getId());
            dto.setAuthor(comment.get().getAuthor());
            dto.setContent(comment.get().getContent());
            dto.setCreatedTime(comment.get().getCreatedTime());
            dto.setUpdatedTime(comment.get().getUpdatedTime());
            dto.setPostId(comment.get().getId());

            return new ResponseEntity<>(dto, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>("Invalid UUID format", HttpStatus.BAD_REQUEST);
            throw new UserNotFoundException("Invalid UUID format");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/update/{commentId}")
    public ResponseEntity<?> updateComment(@Valid @RequestBody CommentDto commentDto, @PathVariable String commentId) {

        Optional<Comments> comments = commentRepository.findById(commentId);

        if (!comments.isPresent()) {
            throw new UserNotFoundException("No Comment found at given " + commentId + "ID");
        }

        Comments existingComment = comments.get();

        existingComment.setContent(commentDto.getContent());
        existingComment.setUpdatedTime(LocalDateTime.now());

        Comments updatedComment = commentRepository.save(existingComment);

        CommentDto updatedDto = CommentDto.builder()
                .id(updatedComment.getId())
                .content(updatedComment.getContent())
                .author(updatedComment.getAuthor())
                .createdTime(updatedComment.getCreatedTime())
                .updatedTime(updatedComment.getUpdatedTime())
                .postId(updatedComment.getPost().getId())
                .build();

        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    //  - Delete Comment: DELETE /comments/{id}
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable String commentId) {

        Optional<Comments> comments = commentRepository.findById(commentId);

        if (!comments.isPresent()) {
            throw new UserNotFoundException("No Comment found at " + commentId + " ID");
        }

        Comments newComments1 = comments.get();
        commentRepository.delete(newComments1);

        return new ResponseEntity<>("Comment Deleted !!", HttpStatus.OK);
    }

}
