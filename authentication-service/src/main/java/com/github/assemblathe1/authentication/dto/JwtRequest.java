package com.github.assemblathe1.authentication.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
