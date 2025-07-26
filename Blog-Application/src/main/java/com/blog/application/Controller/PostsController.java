package com.blog.application.Controller;

import com.blog.application.Dto.CommentDto;
import com.blog.application.Dto.PostsDto;
import com.blog.application.Entity.Posts;
import com.blog.application.Entity.User;
import com.blog.application.Exception.UserNotFoundException;
import com.blog.application.Repository.PostsRepository;
import com.blog.application.Repository.UserRepository;
import com.blog.application.Service.PostsService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostsController {

    @Autowired
    private PostsService postsService;

    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(PostsController.class);

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostsDto postsDto) {

        List<Posts> check = postsRepository.findByTitle(postsDto.getTitle());
        for (Posts post : check) {
            if (post.getTitle().equalsIgnoreCase(postsDto.getTitle())) {
                throw new UserNotFoundException("Post with title '" + postsDto.getTitle() + "' already exists.");
            }
        }

        postsDto.setAuthor(getUserName());
        Posts posts = postsService.createPosts(postsDto);

        // Map posts to response DTO
        PostsDto responseDto = mapper.map(posts, PostsDto.class);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    public String getUserName() {
        User user = userRepository.findByEmail(getCurrentUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getUsername();
    }


    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();     }



    @GetMapping("/getAll")
    public ResponseEntity<?> getAllPosts() {
        List<Posts> allPosts = postsRepository.findAll();

        List<PostsDto> postDtos = allPosts.stream().map(post -> {
            List<CommentDto> commentDtos = post.getComments().stream().map(comment ->
                    CommentDto.builder()
                            .id(comment.getId())
                            .postId(post.getId())
                            .content(comment.getContent())
                            .author(comment.getAuthor())
                            .createdTime(comment.getCreatedTime())
                            .updatedTime(comment.getUpdatedTime())
                            .build()
            ).toList();

            return PostsDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .author(post.getAuthor())
                    .createdTime(post.getCreatedTime())
                    .updatedTime(post.getUpdatedTime())
                    .comments(commentDtos)
                    .build();
        }).toList();

        return ResponseEntity.ok(postDtos);
    }


    @GetMapping("/getSingle/{stringId}")
    public ResponseEntity<?> getSinglePost(@PathVariable("stringId") String stringId) {
        try {


            Posts post = postsRepository.findById(stringId).orElse(null);

            if (post == null) {
//                return new ResponseEntity<>("Not Found At given Id", HttpStatus.NOT_FOUND);
                throw new UserNotFoundException("Post is Not Found At "+stringId + " Id ");
            }

            PostsDto postDto = mapper.map(post, PostsDto.class);
            return new ResponseEntity<>(postDto, HttpStatus.OK);

        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>("Invalid UUID format", HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePosts(@PathVariable String id) {

        Optional<Posts> find = postsRepository.findById(id);
        logger.info(" Find: {}", find);
        if (find.isEmpty()) {
//            return new ResponseEntity<>("Not Found at given ID", HttpStatus.NOT_FOUND);
            throw new UserNotFoundException("Not Found at given "+id +" ID");
        } else {
            postsRepository.deleteById(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        }
    }


    //Update
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePosts(  @Valid @RequestBody PostsDto postsDto, @PathVariable String id) {
        Posts posts = postsService.updatePost(postsDto, id);
        return new ResponseEntity<>(posts, HttpStatus.CREATED);
    }







}
