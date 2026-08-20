package com.example.inventario_activos.domain.port;

import java.util.List;

import com.example.inventario_activos.domain.model.AssetHistory;

public interface AssetHistoryRepositoryPort {

    AssetHistory save(AssetHistory history);
    
    List<AssetHistory> findByAssetId(Long assetId);

}
