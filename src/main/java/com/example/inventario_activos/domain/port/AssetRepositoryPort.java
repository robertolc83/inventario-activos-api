package com.example.inventario_activos.domain.port;

import java.util.List;
import java.util.Optional;

import com.example.inventario_activos.domain.model.Asset;

public interface AssetRepositoryPort {

    Asset save(Asset asset);

    Optional<Asset> findById(Long id);

    Optional<Asset> findByFolio(String folio);

    List<Asset> findAll();
    
    boolean existsBySerialNumber(String serialNumber);

    long countByFolioStartingWith(String prefix);

}
