package com.example.inventario_activos.application.usecase;

import java.util.List;

import com.example.inventario_activos.application.dto.request.AssetRequestDTO;
import com.example.inventario_activos.application.dto.request.AssetHistoryRequestDTO;
import com.example.inventario_activos.application.dto.response.AssetResponseDTO;

public interface AssetUseCase {

    AssetResponseDTO createAsset(AssetRequestDTO request);

    AssetResponseDTO updateAsset(Long id, AssetRequestDTO request);

    AssetResponseDTO updateAssetStatus(Long id, AssetHistoryRequestDTO request);

    List<AssetResponseDTO> getAllAssets();
    
    AssetResponseDTO getAssetById(Long id);

}
