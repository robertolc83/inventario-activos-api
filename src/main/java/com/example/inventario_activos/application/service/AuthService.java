package com.example.inventario_activos.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.inventario_activos.application.usecase.AuthUseCase;
import com.example.inventario_activos.domain.model.User;
import com.example.inventario_activos.domain.port.UserRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase{

    private final UserRepositoryPort userRepositoryPortport;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepositoryPortport.findByUsername(username);
    }

}
