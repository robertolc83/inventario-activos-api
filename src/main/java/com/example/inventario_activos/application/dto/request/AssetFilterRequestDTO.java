package com.example.inventario_activos.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetFilterRequestDTO {

    private String searchTerm;

    private String status;

    private Long categoryId;

    private Double minCost;
    
    private Double maxCost;
}
