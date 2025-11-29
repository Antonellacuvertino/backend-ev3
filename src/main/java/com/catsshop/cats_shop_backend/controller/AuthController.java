package com.catsshop.cats_shop_backend.controller;

import com.catsshop.cats_shop_backend.model.User;
import com.catsshop.cats_shop_backend.service.AuthService;
import com.catsshop.cats_shop_backend.util.JwtUtil; // Asegúrate de que esta clase exista en el paquete util
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil; // Necesitas esta clase en el paquete util

    // Endpoint de registro
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) {
        try {
            User registeredUser = authService.registerNewUser(user);

            // Generar token
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", registeredUser.getId());
            claims.put("username", registeredUser.getUsername());
            claims.put("role", registeredUser.getRole().name());

            // ⚠️ CORRECCIÓN (Línea ~38) ⚠️: Solo pasamos claims (Map) y username (String)
            String token = jwtUtil.generateToken(claims, registeredUser.getUsername());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", registeredUser);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Error durante el registro: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint de login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        User user = authService.authenticate(username, password);

        if (user != null) {
            // Generar token
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId());
            claims.put("username", user.getUsername());
            claims.put("role", user.getRole().name());

            // ⚠️ CORRECCIÓN (Línea ~70) ⚠️: Solo pasamos claims (Map) y username (String)
            String token = jwtUtil.generateToken(claims, user.getUsername());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", user);

            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Credenciales inválidas");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
    }
}