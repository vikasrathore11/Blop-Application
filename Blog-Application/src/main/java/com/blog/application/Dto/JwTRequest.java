package com.blog.application.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwTRequest {
    private String email;
    private String password;
}
