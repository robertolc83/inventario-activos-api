package com.example.inventario_activos.domain.exception.asset;

public class AssetNotFoundException extends RuntimeException{

    public AssetNotFoundException(Long id) {
        super("No se encontró el activo con el ID: " + id);
    }

}
