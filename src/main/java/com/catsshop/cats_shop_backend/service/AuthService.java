package com.catsshop.cats_shop_backend.service;

import com.catsshop.cats_shop_backend.model.Role; // Asegúrate de importar Role
import com.catsshop.cats_shop_backend.model.User;
import com.catsshop.cats_shop_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User newUser) {
        // Codificar la contraseña antes de guardar
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        // El error estaba aquí: si el DTO tiene un String 'role', necesitamos convertirlo a Enum.
        // Asumiremos un rol por defecto si no viene especificado o si es el registro inicial.
        // Si el rol viene en el DTO, necesitarías un DTO. Por ahora, asumiremos que se asigna 'CLIENT' por defecto.

        // Usamos .valueOf() para convertir el String "CLIENT" al Enum Role.CLIENT
        // Si el rol viniera en newUser, sería: Role.valueOf(newUser.getRoleString().toUpperCase())
        newUser.setRole(Role.valueOf("CLIENT"));

        return userRepository.save(newUser);
    }

    // Método de autenticación (login)
    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);

        // Verificación de existencia y contraseña
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}