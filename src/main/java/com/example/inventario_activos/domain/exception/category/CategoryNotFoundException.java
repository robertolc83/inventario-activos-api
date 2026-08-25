package com.example.inventario_activos.domain.exception.category;

public class CategoryNotFoundException extends RuntimeException{
    
    public CategoryNotFoundException(Long id) {
        super("No se encontró la categoría con el ID: " + id);
    }

}
