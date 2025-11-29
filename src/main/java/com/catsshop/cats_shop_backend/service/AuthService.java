package com.catsshop.cats_shop_backend.service;

import com.catsshop.cats_shop_backend.model.User;
import com.catsshop.cats_shop_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional; // Importa si usas esta anotación

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor // Usado para inyección automática de dependencias
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerNewUser(String username, String rawPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("El nombre de usuario o email ya existe.");
        }

        User newUser = new User();
        newUser.setUsername(username);

        // ⚠️ CORRECCIÓN CRÍTICA: CODIFICAR LA CONTRASEÑA ⚠️
        newUser.setPassword(passwordEncoder.encode(rawPassword));

        // ⚠️ ASIGNAR UN ROL POR DEFECTO ⚠️
        newUser.setRole("CLIENT");

        // Si tienes este campo, descoméntalo
        // newUser.setCreationDate(LocalDateTime.now());

        return userRepository.save(newUser);
    }

    // Método auxiliar para el login
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}