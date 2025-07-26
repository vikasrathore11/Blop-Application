package com.blog.application.Service;

import com.blog.application.Dto.UserRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@Component
public interface UserService {



    UserRequestDTO create(UserRequestDTO userRequestDTO);
}
