package com.blog.application.Repository;

import com.blog.application.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role,String> {

    Optional<Role> findByName(String name);
}
