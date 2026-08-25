package com.example.inventario_activos.application.dto.request;

import com.example.inventario_activos.domain.enums.AssetStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetHistoryRequestDTO {

    @NotNull(message = "El nuevo estado es obligatorio")
    private AssetStatus newStatus;

    @NotBlank(message = "La justificación del cambio es obligatoria")
    private String justification;

    @NotBlank(message = "El usuario que realiza el cambio es obligatorio")
    private String changedBy;

    private String assignedTo; // Requerido si el estado pasa a ASSIGNED

}
