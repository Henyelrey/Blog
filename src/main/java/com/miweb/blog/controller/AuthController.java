package com.miweb.blog.controller;

import com.miweb.blog.dto.AuthRequest;
import com.miweb.blog.dto.AuthResponse;
import com.miweb.blog.model.User;
import com.miweb.blog.repository.UserRepository;
import com.miweb.blog.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint de Registro
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya existe");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        // IMPORTANTE: Encriptar la contraseña antes de guardarla
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER"); // Por defecto todos son usuarios normales

        userRepository.save(user);

        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    // Endpoint de Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // 1. Esto autentica contra la base de datos automáticamente usando Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // 2. Si las credenciales son correctas, generamos el token
            String token = jwtUtil.generateToken(request.getUsername());

            // 3. Devolvemos el token en un JSON
            return ResponseEntity.ok(new AuthResponse(token));

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}