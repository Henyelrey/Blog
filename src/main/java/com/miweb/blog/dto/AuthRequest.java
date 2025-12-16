package com.miweb.blog.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
    private String email; // Opcional para login, necesario para registro
}