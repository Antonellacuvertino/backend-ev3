
package com.catsshop.cats_shop_backend.repository;

import com.catsshop.cats_shop_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Necesario para buscar al registrar y al iniciar sesión
    Optional<User> findByUsername(String username);
}