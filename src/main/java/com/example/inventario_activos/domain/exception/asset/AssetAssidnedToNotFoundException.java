package com.example.inventario_activos.domain.exception.asset;

public class AssetAssidnedToNotFoundException extends RuntimeException {
    
    public AssetAssidnedToNotFoundException() {
        super("Un activo asignado debe tener un responsable");
    }

}
