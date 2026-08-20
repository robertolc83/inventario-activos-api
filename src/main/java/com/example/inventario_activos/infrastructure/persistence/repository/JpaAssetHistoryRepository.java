package com.example.inventario_activos.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventario_activos.domain.model.AssetHistory;

public interface JpaAssetHistoryRepository extends JpaRepository<AssetHistory, Long> {

    List<AssetHistory> findByAssetId(Long assetId);

}
