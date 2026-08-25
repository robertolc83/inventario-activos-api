package com.example.inventario_activos.domain.exception.category;

public class CategoryCodeAlreadyExistsException extends RuntimeException{
    
    public CategoryCodeAlreadyExistsException(String code) {
        super("Ya existe una categoría con el código: " + code);
    }
}
