package com.example.inventario_activos.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventario_activos.domain.model.Asset;

public interface JpaAssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByFolio(String folio);

    boolean existsBySerialNumber(String serialNumber);

}
