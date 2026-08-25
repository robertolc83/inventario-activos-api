package com.example.inventario_activos.infrastructure.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.inventario_activos.domain.exception.asset.AssetAssidnedToNotFoundException;
import com.example.inventario_activos.domain.exception.asset.AssetNewStatusEquialToStatusException;
import com.example.inventario_activos.domain.exception.asset.AssetNotFoundException;
import com.example.inventario_activos.domain.exception.asset.AssetRetiredStatusException;
import com.example.inventario_activos.domain.exception.asset.AssetSerialNumberAlreadyExistsException;
import com.example.inventario_activos.domain.exception.category.CategoryCodeAlreadyExistsException;
import com.example.inventario_activos.domain.exception.category.CategoryNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleCategoryNotFound(CategoryNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AssetNotFoundException.class)
    public ResponseEntity<ApiError> handleAssetNotFound(AssetNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CategoryCodeAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleCategoryCodeAlreadyExists(CategoryCodeAlreadyExistsException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AssetSerialNumberAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleAssetSerialNumberAlreadyExists(AssetSerialNumberAlreadyExistsException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AssetNewStatusEquialToStatusException.class)
    public ResponseEntity<ApiError> handleAssetStatusEqual(AssetNewStatusEquialToStatusException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AssetAssidnedToNotFoundException.class)
    public ResponseEntity<ApiError> handleAssetAssignedToNotFound(AssetAssidnedToNotFoundException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    
    @ExceptionHandler(AssetRetiredStatusException.class)
    public ResponseEntity<ApiError> handleAssetRetiredStatus(AssetRetiredStatusException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor: " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Error de validación en la petición");

        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    private ResponseEntity<ApiError> buildErrorResponse(HttpStatus status, String message) {
        ApiError error = new ApiError(
                status.value(),
                message,
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(error);
    }

}
