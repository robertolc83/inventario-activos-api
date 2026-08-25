package com.example.inventario_activos.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.inventario_activos.application.dto.request.AssetRequestDTO;
import com.example.inventario_activos.application.dto.request.AssetHistoryRequestDTO;
import com.example.inventario_activos.application.dto.response.AssetResponseDTO;
import com.example.inventario_activos.application.dto.response.CategoryResponseDTO;
import com.example.inventario_activos.application.usecase.AssetUseCase;
import com.example.inventario_activos.domain.enums.AssetStatus;
import com.example.inventario_activos.domain.exception.asset.AssetNotFoundException;
import com.example.inventario_activos.domain.exception.asset.AssetRetiredStatusException;
import com.example.inventario_activos.domain.exception.asset.AssetSerialNumberAlreadyExistsException;
import com.example.inventario_activos.domain.exception.category.CategoryNotFoundException;
import com.example.inventario_activos.domain.model.Asset;
import com.example.inventario_activos.domain.model.AssetHistory;
import com.example.inventario_activos.domain.model.Category;
import com.example.inventario_activos.domain.port.AssetHistoryRepositoryPort;
import com.example.inventario_activos.domain.port.AssetRepositoryPort;
import com.example.inventario_activos.domain.port.CategoryRepositoryPort;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssetService implements AssetUseCase{

    private final AssetRepositoryPort assetRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;
    private final AssetHistoryRepositoryPort assetHistoryRepositoryPort;

    @Override
    @Transactional
    public AssetResponseDTO createAsset(AssetRequestDTO request) {
        
        if (assetRepositoryPort.existsBySerialNumber(request.getSerialNumber())) {
            throw new AssetSerialNumberAlreadyExistsException(request.getSerialNumber());
        }

        Category category = categoryRepositoryPort.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));

        String folio = generateFolio(category.getCode());

        Asset asset = Asset.create(
            folio,
            request.getSerialNumber(),
            request.getModel(),
            request.getAcquisitionCost(),
            request.getDateOfEntry(),
            request.getLocation(),
            category
        );

        Asset savedAsset = assetRepositoryPort.save(asset);

        saveHistory(
            savedAsset, 
            null, 
            savedAsset.getStatus(),
            "Registro inicial del activo", 
            "SYSTEM"
        );

        return mapToResponse(savedAsset);
    }

    @Override
    @Transactional
    public AssetResponseDTO updateAsset(Long id, AssetRequestDTO request) {

        Asset existingAsset = assetRepositoryPort.findById(id)
            .orElseThrow(() -> new AssetNotFoundException(id));

        if(existingAsset.isRetired()){
            throw new AssetRetiredStatusException();
        }
        
        Category category = categoryRepositoryPort.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));

        existingAsset.setSerialNumber(request.getSerialNumber());
        existingAsset.setModel(request.getModel());
        existingAsset.setAcquisitionCost(request.getAcquisitionCost());
        existingAsset.setDateOfEntry(request.getDateOfEntry());
        existingAsset.setLocation(request.getLocation());
        existingAsset.setCategory(category);

        Asset updatedAsset = assetRepositoryPort.save(existingAsset);

        saveHistory(
            updatedAsset, 
            updatedAsset.getStatus(),
            updatedAsset.getStatus(),
            "Actualización de datos generales del activo",
            "SYSTEM"
        );

        return mapToResponse(updatedAsset);
    }

    @Override
    @Transactional
    public AssetResponseDTO updateAssetStatus(Long id, AssetHistoryRequestDTO request) {

        Asset asset = assetRepositoryPort.findById(id)
                .orElseThrow(() -> new AssetNotFoundException(id));

        AssetStatus previousStatus =
            asset.changeStatus(request.getNewStatus(), request.getAssignedTo());

        Asset updatedAsset = assetRepositoryPort.save(asset);

        saveHistory(
                updatedAsset,
                previousStatus,
                request.getNewStatus(),
                request.getJustification(),
                request.getChangedBy()
        );
        
        return mapToResponse(updatedAsset);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetResponseDTO> getAllAssets() {
        return assetRepositoryPort.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AssetResponseDTO getAssetById(Long id) {
        Asset asset = assetRepositoryPort.findById(id)
                .orElseThrow(() -> new AssetNotFoundException(id));
        return mapToResponse(asset);
    }

    /*TODO: Revisar más adelante si se puede optimizar el metodo de generacion de folio
    // private String generateFolio(String categoryPrefix) {
    //     int currentYear = LocalDate.now().getYear();
    //     String prefixYearPattern = categoryPrefix + "-" + currentYear;
        
    //     long count = assetRepositoryPort.findAll().stream()
    //             .filter(a -> a.getFolio() != null && a.getFolio().startsWith(prefixYearPattern))
    //             .count();

    //     return String.format("%s-%d-%04d", categoryPrefix, currentYear, count + 1);
    // }**/

    /*TODO: hacer una tabla de consecutivos por categoria
        folio_sequences
        ----------------
        id
        category_code
        year
        last_number
    */
private String generateFolio(String categoryPrefix) {
    int currentYear = LocalDate.now().getYear();
    String prefix = String.format("%s-%d-", categoryPrefix, currentYear);
    long consecutive = assetRepositoryPort.countByFolioStartingWith(prefix) + 1;
    return String.format("%s%04d", prefix, consecutive); // "LAP-2026-0001"
}

    private void saveHistory(
        Asset asset,
        AssetStatus oldStatus,
        AssetStatus newStatus,
        String justification,
        String changedBy) {

        AssetHistory history = AssetHistory.create(
            asset,
            oldStatus,
            newStatus,
            justification,
            changedBy
        );

        assetHistoryRepositoryPort.save(history);
    }

    private AssetResponseDTO mapToResponse(Asset asset) {
        AssetResponseDTO dto = new AssetResponseDTO();
        dto.setId(asset.getId());
        dto.setFolio(asset.getFolio());
        dto.setSerialNumber(asset.getSerialNumber());
        dto.setModel(asset.getModel());
        dto.setStatus(asset.getStatus());
        dto.setAcquisitionCost(asset.getAcquisitionCost());
        dto.setDateOfEntry(asset.getDateOfEntry());
        dto.setLocation(asset.getLocation());
        dto.setAssignedTo(asset.getAssignedTo());

        if (asset.getCategory() != null) {
            dto.setCategory(new CategoryResponseDTO(
                    asset.getCategory().getId(),
                    asset.getCategory().getName(),
                    asset.getCategory().getCode()
            ));
        }

        return dto;
    }

}
