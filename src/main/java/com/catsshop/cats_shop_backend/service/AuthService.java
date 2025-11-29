package com.catsshop.cats_shop_backend.service;

import com.catsshop.cats_shop_backend.model.Role;
import com.catsshop.cats_shop_backend.model.User;
import com.catsshop.cats_shop_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional; // Necesitas importar Optional

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        // Asume el rol 'CLIENT' si no se especifica.
        newUser.setRole(Role.valueOf("CLIENT"));

        return userRepository.save(newUser);
    }

    // Método de autenticación (login)
    public User authenticate(String username, String password) {
        // Asumimos que userRepository.findByUsername devuelve Optional<User>
        Optional<User> optionalUser = userRepository.findByUsername(username);

        // ⚠️ CORRECCIÓN (Línea ~36) ⚠️: Desempaquetamos el Optional de forma segura
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;

        // Alternativa más concisa:
        // User user = userRepository.findByUsername(username).orElse(null);
        // if (user != null && passwordEncoder.matches(password, user.getPassword())) {
        //     return user;
        // }
        // return null;
    }
}