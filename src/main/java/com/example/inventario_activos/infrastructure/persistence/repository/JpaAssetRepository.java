package com.example.inventario_activos.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.inventario_activos.domain.model.Asset;

public interface JpaAssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByFolio(String folio);

    boolean existsBySerialNumber(String serialNumber);

    long countByFolioStartingWith(String prefix);

    @Query("SELECT a FROM Asset a JOIN FETCH a.category")
    List<Asset> findAllWithCategory();

}
