package com.blog.application.Service;

import com.blog.application.Dto.PostsDto;
import com.blog.application.Entity.Posts;
import org.springframework.stereotype.Component;

@Component
public interface PostsService {

     Posts createPosts(PostsDto postsDto);

     Posts updatePost(PostsDto postsDto,String id);

}
