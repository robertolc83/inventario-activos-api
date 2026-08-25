package com.example.inventario_activos.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.inventario_activos.application.dto.request.CategoryRequestDTO;
import com.example.inventario_activos.application.dto.response.CategoryResponseDTO;
import com.example.inventario_activos.application.usecase.CategoryUseCase;
import com.example.inventario_activos.domain.exception.category.CategoryCodeAlreadyExistsException;
import com.example.inventario_activos.domain.exception.category.CategoryNotFoundException;
import com.example.inventario_activos.domain.model.Category;
import com.example.inventario_activos.domain.port.CategoryRepositoryPort;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryUseCase{

    private final CategoryRepositoryPort repositoryPort;

    @Override
    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO request) {

        String upperCode = request.getCode().toUpperCase().trim();
        
        if (repositoryPort.existsByCode(upperCode)) {
            throw new CategoryCodeAlreadyExistsException(upperCode);
        }

        Category category = Category.create(request.getName(), upperCode);

        Category saved = repositoryPort.save(category);
        return mapToResponse(saved);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {

        return repositoryPort.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {

        Category category = repositoryPort.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        return mapToResponse(category);
    }

    private CategoryResponseDTO mapToResponse(Category category) {

        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getCode()
        );
    }

}
