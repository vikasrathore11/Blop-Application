package com.blog.application.Service.Impl;

import com.blog.application.Dto.PostsDto;
import com.blog.application.Entity.Comments;
import com.blog.application.Entity.Posts;
import com.blog.application.Repository.PostsRepository;
import com.blog.application.Service.PostsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Posts createPosts(PostsDto postsDto) {
        LocalDateTime localDateTime = LocalDateTime.now();

        Posts posts = new Posts();
        posts.setId(UUID.randomUUID().toString());
        posts.setAuthor(postsDto.getAuthor());
        posts.setTitle(postsDto.getTitle());
        posts.setContent(postsDto.getContent());
        posts.setCreatedTime(localDateTime);
        posts.setUpdatedTime(localDateTime);
        posts.setComments(new ArrayList<>());

        postsRepository.save(posts);
        return posts;
    }


    @Override
    public Posts updatePost(PostsDto postsDto, String id) {

        Posts posts = postsRepository.findById(id).orElse(null);

        Posts postsDto1 = Posts.builder()
                .id(posts.getId())
                .comments(new ArrayList<>())
                .content(postsDto.getContent())
                .author(posts.getAuthor())
                .title(postsDto.getTitle())
                .updatedTime(LocalDateTime.now())
                .createdTime(posts.getCreatedTime())
                .build();

        Posts map = mapper.map(postsDto1, Posts.class);
        postsRepository.save(map);
        return map;
    }
}