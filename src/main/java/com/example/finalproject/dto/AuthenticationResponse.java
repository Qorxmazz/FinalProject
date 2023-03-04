package com.example.finalproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    String email;
    String token;
}
