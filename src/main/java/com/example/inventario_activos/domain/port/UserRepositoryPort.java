package com.example.inventario_activos.domain.port;

import java.util.Optional;

import com.example.inventario_activos.domain.model.User;

public interface UserRepositoryPort {

    Optional<User> findByUsername(String username);
}
