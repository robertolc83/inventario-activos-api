package com.example.inventario_activos.application.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetRequestDTO {

    @NotBlank(message = "El número de serie es obligatorio")
    private String serialNumber;

    private String model;

    @NotNull(message = "El costo de adquisición es obligatorio")
    @Positive(message = "El costo debe ser un valor positivo")
    private BigDecimal acquisitionCost;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    private LocalDate dateOfEntry;

    private String location;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoryId;

}
