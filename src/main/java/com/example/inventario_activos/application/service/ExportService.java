package com.example.inventario_activos.application.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.inventario_activos.application.dto.request.AssetFilterRequestDTO;
import com.example.inventario_activos.application.dto.response.ExportZipResponseDTO;
import com.example.inventario_activos.domain.model.Asset;
import com.example.inventario_activos.infrastructure.persistence.repository.JpaAssetRepository;
import com.example.inventario_activos.infrastructure.persistence.repository.specs.AssetSpecification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExportService {

    private final JpaAssetRepository assetRepository;

    @Transactional(readOnly = true)
    public ExportZipResponseDTO generateAssetsZipReport(AssetFilterRequestDTO filter) {
        // 1. Consultar BD con especificaciones
        List<Asset> assets = assetRepository.findAll(AssetSpecification.filterByParams(filter));

        try (ByteArrayOutputStream zipByteStream = new ByteArrayOutputStream();
             ZipOutputStream zipOut = new ZipOutputStream(zipByteStream)) {

            // 2. Generar Excel e incluir en el ZIP
            byte[] excelBytes = generateExcelReport(assets);
            ZipEntry excelEntry = new ZipEntry("reporte_activos.xlsx");
            zipOut.putNextEntry(excelEntry);
            zipOut.write(excelBytes);
            zipOut.closeEntry();

            // 3. Generar TXT de Auditoría e incluir en el ZIP
            byte[] auditBytes = generateAuditFile(assets.size());
            ZipEntry auditEntry = new ZipEntry("auditoria.txt");
            zipOut.putNextEntry(auditEntry);
            zipOut.write(auditBytes);
            zipOut.closeEntry();

            zipOut.finish();
            zipOut.flush();

            // 4. Convertir ZIP completo a Base64
            byte[] zipBytes = zipByteStream.toByteArray();
            String base64Encoded = Base64.getEncoder().encodeToString(zipBytes);

            return ExportZipResponseDTO.builder()
                    .status(200)
                    .message("Reporte generado correctamente")
                    .fileName("inventario_activos.zip")
                    .fileBase64(base64Encoded)
                    .build();

        } catch (IOException e) {
            log.error("Error al generar el archivo ZIP de activos: ", e);
            throw new RuntimeException("Error al construir el archivo ZIP", e);
        }
    }

    private byte[] generateExcelReport(List<Asset> assets) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Activos");

            // Crear Encabezado
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Folio", "N° Serie", "Modelo", "Costo", "Ubicación", "Categoría", "Estado"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Llenar Filas
            int rowIdx = 1;
            for (Asset asset : assets) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(asset.getFolio() != null ? asset.getFolio() : "N/A");
                row.createCell(1).setCellValue(asset.getSerialNumber());
                row.createCell(2).setCellValue(asset.getModel());
                row.createCell(3).setCellValue(asset.getAcquisitionCost().doubleValue());
                row.createCell(4).setCellValue(asset.getLocation());
                row.createCell(5).setCellValue(asset.getCategory() != null ? asset.getCategory().getName() : "N/A");
                row.createCell(6).setCellValue(asset.getStatus().name());
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    private byte[] generateAuditFile(int totalRecords) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String currentDateTime = LocalDateTime.now().format(formatter);

        // Obtiene el usuario autenticado desde el JWT
        String currentUser = SecurityContextHolder.getContext().getAuthentication() != null 
            ? SecurityContextHolder.getContext().getAuthentication().getName() 
            : "ANONYMOUS";

        StringBuilder sb = new StringBuilder();
        sb.append("=== DOCUMENTO DE AUDITORÍA ===\n\n");
        sb.append("Fecha y Hora de Generación: ").append(currentDateTime).append("\n");
        sb.append("Usuario Solicitante: ").append(currentUser).append("\n");
        sb.append("Total de Registros Exportados: ").append(totalRecords).append("\n");

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

}
