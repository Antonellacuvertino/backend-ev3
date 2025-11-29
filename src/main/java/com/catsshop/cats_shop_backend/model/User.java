package com.catsshop.cats_shop_backend.model;

import jakarta.persistence.*;
import lombok.Data; // 👈 NECESARIO PARA GETTERS/SETTERS/toString/equals
import lombok.NoArgsConstructor; // 👈 NECESARIO PARA JPA



@Entity
@Table(name = "usuarios")
@Data // Genera automáticamente: getters, setters, hashCode, equals, y toString
@NoArgsConstructor // Genera un constructor sin argumentos (requerido por JPA)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // Si la clase Role existe como un Enum en tu proyecto, esto es correcto.
    @Enumerated(EnumType.STRING)
    private Role role;

    // Aquí puedes añadir otros campos como email, nombre, etc.
}