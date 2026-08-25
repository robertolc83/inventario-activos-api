package com.example.inventario_activos.infrastructure.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventario_activos.application.dto.request.AssetFilterRequestDTO;
import com.example.inventario_activos.application.dto.request.AssetHistoryRequestDTO;
import com.example.inventario_activos.application.dto.request.AssetRequestDTO;
import com.example.inventario_activos.application.dto.response.AssetResponseDTO;
import com.example.inventario_activos.application.dto.response.ExportZipResponseDTO;
import com.example.inventario_activos.application.service.ExportService;
import com.example.inventario_activos.application.usecase.AssetUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetUseCase assetUseCase;
    private final ExportService exportService;

    @PostMapping
    public ResponseEntity<AssetResponseDTO> createAsset(@Valid @RequestBody AssetRequestDTO request) {
        AssetResponseDTO response = assetUseCase.createAsset(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/export/zip")
    public ResponseEntity<ExportZipResponseDTO> exportAssetsToZip(@RequestBody AssetFilterRequestDTO filters) {
        ExportZipResponseDTO response = exportService.generateAssetsZipReport(filters);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetResponseDTO> updateAsset(@PathVariable Long id, @Valid  @RequestBody AssetRequestDTO request) {
        AssetResponseDTO response = assetUseCase.updateAsset(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AssetResponseDTO> updateAssetStatus(@PathVariable Long id, @Valid @RequestBody AssetHistoryRequestDTO request) {
        AssetResponseDTO response = assetUseCase.updateAssetStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AssetResponseDTO>> getAllAssets() {
        return ResponseEntity.ok(assetUseCase.getAllAssets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponseDTO> getAssetById(@PathVariable Long id) {
        return ResponseEntity.ok(assetUseCase.getAssetById(id));
    }

}
