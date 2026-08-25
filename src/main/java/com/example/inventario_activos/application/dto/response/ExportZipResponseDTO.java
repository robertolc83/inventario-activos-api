package com.example.inventario_activos.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportZipResponseDTO {

    private int status;

    private String message;

    private String fileName;

    private String fileBase64;
}
