package com.example.inventario_activos.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.inventario_activos.domain.enums.AssetStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponseDTO {

    private Long id;

    private String folio;

    private String serialNumber;

    private String model;

    private AssetStatus status;

    private BigDecimal acquisitionCost;

    private LocalDate dateOfEntry;

    private String location;

    private String assignedTo;

    private CategoryResponseDTO category;

}
