package com.example.inventario_activos.application.usecase;

import java.util.Optional;

import com.example.inventario_activos.domain.model.User;

public interface AuthUseCase {
    Optional<User> findByUsername(String username);

}
