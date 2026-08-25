package com.example.inventario_activos.domain.exception.asset;

import com.example.inventario_activos.domain.enums.AssetStatus;

public class AssetNewStatusEquialToStatusException extends RuntimeException {
    
    public AssetNewStatusEquialToStatusException(AssetStatus currentStatus) {
        super("El activo ya se encuentra en estado: " + currentStatus);
    }

}
