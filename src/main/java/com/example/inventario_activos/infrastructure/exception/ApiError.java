package com.example.inventario_activos.infrastructure.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiError {

    private final int status;
    private final String message;
    private final LocalDateTime timestamp;

}
