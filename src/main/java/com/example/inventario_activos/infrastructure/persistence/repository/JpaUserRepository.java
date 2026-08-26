package com.example.inventario_activos.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventario_activos.domain.model.User;

public interface JpaUserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
