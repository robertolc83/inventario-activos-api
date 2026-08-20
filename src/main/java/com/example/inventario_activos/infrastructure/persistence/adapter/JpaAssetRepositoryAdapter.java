package com.example.inventario_activos.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.inventario_activos.domain.model.Asset;
import com.example.inventario_activos.domain.port.AssetRepositoryPort;
import com.example.inventario_activos.infrastructure.persistence.repository.JpaAssetRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaAssetRepositoryAdapter implements AssetRepositoryPort {

    private final JpaAssetRepository repository;

    @Override
    public Asset save(Asset asset) {
        return repository.save(asset);
    }

    @Override
    public Optional<Asset> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Asset> findByFolio(String folio) {
        return repository.findByFolio(folio);
    }

    @Override
    public List<Asset> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean existsBySerialNumber(String serialNumber) {
        return repository.existsBySerialNumber(serialNumber);
    }

}
