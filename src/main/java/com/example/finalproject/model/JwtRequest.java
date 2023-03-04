package com.example.finalproject.model;

import lombok.Data;

@Data
public class JwtRequest {
    private String email;
    private String password;
}