package com.blog.application;

import com.blog.application.Entity.Role;
import com.blog.application.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }


    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {


        Role roleNormal = roleRepository.findByName("ROLE_NORMAL").orElse(null);
        if (roleNormal == null) {
            roleNormal = new Role();
            roleNormal.setName("ROLE_NORMAL");
            roleNormal.setRoleId(UUID.randomUUID().toString());
            roleRepository.save(roleNormal);
        }


        Role roleNormal1 = roleRepository.findByName("ROLE_ADMIN").orElse(null);
        if (roleNormal1 == null) {
            roleNormal1 = new Role();
            roleNormal1.setName("ROLE_ADMIN");
            roleNormal1.setRoleId(UUID.randomUUID().toString());
            roleRepository.save(roleNormal1);

        }
    }
}
