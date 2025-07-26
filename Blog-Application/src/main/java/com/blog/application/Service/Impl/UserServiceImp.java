package com.blog.application.Service.Impl;

import com.blog.application.Dto.UserRequestDTO;
import com.blog.application.Dto.UserResponse;
import com.blog.application.Entity.Role;
import com.blog.application.Entity.User;
import com.blog.application.Repository.RoleRepository;
import com.blog.application.Repository.UserRepository;
import com.blog.application.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public UserRequestDTO create(UserRequestDTO userRequestDTO) {

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email(userRequestDTO.getEmail())
                .username(userRequestDTO.getUsername())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .build();


        Role role = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Default role not found in DB!"));

        user.setRoles(Set.of(role));
        User savedUser = userRepository.save(user);

        return mapper.map(savedUser, UserRequestDTO.class);
    }



}
