package com.example.inventario_activos.domain.port;

import java.util.List;
import java.util.Optional;

import com.example.inventario_activos.domain.model.Category;

public interface CategoryRepositoryPort {

    Category save(Category category);

    Optional<Category> findById(Long id);

    Optional<Category> findByCode(String code);

    List<Category> findAll();
    
    boolean existsByCode(String code);

}
