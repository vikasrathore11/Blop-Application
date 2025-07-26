package com.blog.application.Repository;

import com.blog.application.Entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, String> {


}
