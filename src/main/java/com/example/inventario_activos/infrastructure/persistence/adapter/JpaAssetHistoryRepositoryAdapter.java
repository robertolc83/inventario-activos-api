package com.example.inventario_activos.infrastructure.persistence.adapter;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.inventario_activos.domain.model.AssetHistory;
import com.example.inventario_activos.domain.port.AssetHistoryRepositoryPort;
import com.example.inventario_activos.infrastructure.persistence.repository.JpaAssetHistoryRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaAssetHistoryRepositoryAdapter implements AssetHistoryRepositoryPort{

    private final JpaAssetHistoryRepository repository;

    @Override
    public AssetHistory save(AssetHistory history) {
        return repository.save(history);
    }

    @Override
    public List<AssetHistory> findByAssetId(Long assetId) {
        return repository.findByAssetId(assetId);
    }

}
