package com.example.inventario_activos.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.inventario_activos.domain.model.Category;
import com.example.inventario_activos.domain.port.CategoryRepositoryPort;
import com.example.inventario_activos.infrastructure.persistence.repository.JpaCategoryRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaCategoryRepositoryAdapter implements CategoryRepositoryPort{

    private final JpaCategoryRepository repository;

    @Override
    public Category save(Category category) {
        return repository.save(category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Category> findByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }

}
