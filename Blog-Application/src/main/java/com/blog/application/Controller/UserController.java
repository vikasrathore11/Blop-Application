package com.blog.application.Controller;

import com.blog.application.Dto.JwTRequest;
import com.blog.application.Dto.JwtResponse;
import com.blog.application.Dto.UserRequestDTO;
import com.blog.application.Entity.User;
import com.blog.application.Exception.UserNotFoundException;
import com.blog.application.Helper.JWTHelper;
import com.blog.application.Repository.RoleRepository;
import com.blog.application.Repository.UserRepository;
import com.blog.application.Service.CustomUserDetailsService;
import com.blog.application.Service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager manager;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserService userService;

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody UserRequestDTO userRequestDTO) {
        boolean b = userRepository.existsByEmail(userRequestDTO.getEmail());
//        logger.info("B {}",b);
//        if (b) {
//            return new ResponseEntity<>("User is A", HttpStatus.NOT_FOUND);
//          return ResponseEntity.ok(throw new UserNotFoundException("AllReady Created  account this email ID" + userRequestDTO.getEmail() + " not found"));
//    }
        if (b) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now().toLocalDate());
            body.put("message", "Already created account with this email ID: " + userRequestDTO.getEmail());
            body.put("status", HttpStatus.BAD_REQUEST.value());
            body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }


        UserRequestDTO userRequestDTO1 = userService.create(userRequestDTO);
        User map = mapper.map(userRequestDTO1, User.class);
//        map.setId(UUID.randomUUID().toString());
//        userRepository.save(map);
        return new ResponseEntity<>(userRequestDTO1, HttpStatus.CREATED);
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<?> loginWithToken(@RequestBody JwTRequest request) {
        logger.info("/auth/login Controller is called");
        this.doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.jwtHelper.generateToken(userDetails);
        User userName = ((CustomUserDetailsService) userDetailsService).getUserEntityByEmail(request.getEmail());
        String actualUsername = userName.getActualUsername();
//        userName.setUsername(actualUsername);
//        System.out.println(actualUsername);
        UserRequestDTO userDto = mapper.map(userName, UserRequestDTO.class);
        userDto.setUsername(actualUsername);
        JwtResponse response = JwtResponse.builder().jwtToken(token).user(userDto).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        try {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, password);
            manager.authenticate(authentication);
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new UserNotFoundException("Invalid email or password!");
        }
    }


    @GetMapping("/current")
    public ResponseEntity<UserRequestDTO> getCurrentUser(Principal principal) {
        String name = principal.getName();
        User user = (User) userDetailsService.loadUserByUsername(name);
        UserRequestDTO dto = mapper.map(user, UserRequestDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
