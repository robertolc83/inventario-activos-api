package com.example.inventario_activos.application.usecase;

import java.util.List;

import com.example.inventario_activos.application.dto.request.CategoryRequestDTO;
import com.example.inventario_activos.application.dto.response.CategoryResponseDTO;

public interface CategoryUseCase {

    CategoryResponseDTO createCategory(CategoryRequestDTO request);

    List<CategoryResponseDTO> getAllCategories();
    
    CategoryResponseDTO getCategoryById(Long id);
}
