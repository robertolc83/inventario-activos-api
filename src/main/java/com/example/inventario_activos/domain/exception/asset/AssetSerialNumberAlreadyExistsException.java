package com.example.inventario_activos.domain.exception.asset;

public class AssetSerialNumberAlreadyExistsException extends RuntimeException {
    
    public AssetSerialNumberAlreadyExistsException(String serialNumber) {
        super("Ya existe un activo con el número de serie: " + serialNumber);
    }

}
