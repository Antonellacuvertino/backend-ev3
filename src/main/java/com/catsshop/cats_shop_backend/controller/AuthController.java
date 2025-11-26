package com.catsshop.cats_shop_backend.controller;

import com.catsshop.cats_shop_backend.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {


        if (!req.username().equals("admin") || !req.password().equals("admin123")) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken("admin", "ADMIN");

        return new LoginResponse(token, "admin", "ADMIN");
    }

    public record LoginRequest(String username, String password) {}
    public record LoginResponse(String token, String username, String role) {}
}
