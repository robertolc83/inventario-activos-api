package com.example.inventario_activos.infrastructure.persistence.adapter;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.inventario_activos.domain.model.User;
import com.example.inventario_activos.domain.port.UserRepositoryPort;
import com.example.inventario_activos.infrastructure.persistence.repository.JpaUserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepositoryPort{

    private final JpaUserRepository repository;

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

}
