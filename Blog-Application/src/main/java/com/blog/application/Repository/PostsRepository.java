package com.blog.application.Repository;

import com.blog.application.Entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts,String > {



    public List<Posts> findByTitle(String posts);

    List<Posts> findAllById(String id);


//    List<Posts> findAllById(String stringid);
}
