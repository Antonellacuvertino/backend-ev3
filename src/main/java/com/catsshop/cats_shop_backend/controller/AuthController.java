package com.catsshop.cats_shop_backend.controller;

import com.catsshop.cats_shop_backend.model.User;
import com.catsshop.cats_shop_backend.security.JwtUtil;
import com.catsshop.cats_shop_backend.service.AuthService; // 👈 NUEVA IMPORTACIÓN
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService; // 👈 INYECTAMOS EL SERVICIO

    // ⚠️ CONSTRUCTOR ACTUALIZADO ⚠️
    public AuthController(JwtUtil jwtUtil, PasswordEncoder passwordEncoder, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    // ⚠️ NUEVO MÉTODO PARA EL REGISTRO ⚠️
    @PostMapping("/register")
    public LoginResponse register(@RequestBody LoginRequest req) {
        // 1. Guarda el usuario usando el servicio (que codifica la contraseña)
        User newUser = authService.registerNewUser(req.username(), req.password());

        // 2. Generar el token (opcional, para iniciar sesión inmediatamente)
        String token = jwtUtil.generateToken(newUser.getUsername(), newUser.getRole());

        // 3. Devolver la respuesta
        return new LoginResponse(token, newUser.getUsername(), newUser.getRole());
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {

        // 1. Buscar usuario
        User user = authService.findByUsername(req.username());

        // 2. Verificar contraseña codificada
        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // 3. Generar token
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return new LoginResponse(token, user.getUsername(), user.getRole());
    }

    public record LoginRequest(String username, String password) {}
    public record LoginResponse(String token, String username, String role) {}
}