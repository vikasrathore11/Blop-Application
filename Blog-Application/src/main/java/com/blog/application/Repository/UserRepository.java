package com.blog.application.Repository;

import com.blog.application.Dto.UserRequestDTO;
import com.blog.application.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<Object> findByUsername(String username);
}
