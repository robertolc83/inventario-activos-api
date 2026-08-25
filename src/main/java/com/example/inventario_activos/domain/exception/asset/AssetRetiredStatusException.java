package com.example.inventario_activos.domain.exception.asset;

public class AssetRetiredStatusException extends RuntimeException{
    public AssetRetiredStatusException() {
        super("Un activo en estado retirado ya no puede ser modificado. ");
    }

}
