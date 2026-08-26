package com.example.inventario_activos.infrastructure.config;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.inventario_activos.application.dto.request.AssetHistoryRequestDTO;
import com.example.inventario_activos.application.dto.request.AssetRequestDTO;
import com.example.inventario_activos.application.dto.request.CategoryRequestDTO;
import com.example.inventario_activos.application.usecase.AssetUseCase;
import com.example.inventario_activos.application.usecase.CategoryUseCase;
import com.example.inventario_activos.domain.enums.AssetStatus;
import com.example.inventario_activos.domain.model.User;
import com.example.inventario_activos.infrastructure.persistence.repository.JpaUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner{

    private final CategoryUseCase categoryUseCase;
    private final AssetUseCase assetUseCase;
    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {
            // Usuario ADMIN
            userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role("ADMIN")
                    .build());

            // Usuario USER
            userRepository.save(User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user123"))
                    .role("USER")
                    .build());
            
             log.info("✅ Usuarios creados: admin/admin123 y user/user123");
        }

        if (!categoryUseCase.getAllCategories().isEmpty()) {
            log.info("Carga de datos omitida: La base de datos ya contiene información.");
            return;
        }

        log.info("Iniciando precarga de datos de prueba...");

        // 1. Crear Categorías
        CategoryRequestDTO catLaptop = new CategoryRequestDTO();
        catLaptop.setName("Laptops y Equipos Cómputo");
        catLaptop.setCode("LAP");
        var laptopCat = categoryUseCase.createCategory(catLaptop);

        CategoryRequestDTO catMonitor = new CategoryRequestDTO();
        catMonitor.setName("Monitores y Pantallas");
        catMonitor.setCode("MON");
        var monitorCat = categoryUseCase.createCategory(catMonitor);

        CategoryRequestDTO catMueble = new CategoryRequestDTO();
        catMueble.setName("Mobiliario de Oficina");
        catMueble.setCode("MOB");
        categoryUseCase.createCategory(catMueble);

        // 2. Crear Activo 1: Laptop (AVAILABLE por defecto)
        AssetRequestDTO lap1 = new AssetRequestDTO();
        lap1.setSerialNumber("SN-THINKPAD-2026-X1");
        lap1.setModel("Lenovo ThinkPad X1 Carbon");
        lap1.setAcquisitionCost(new BigDecimal("28500.00"));
        lap1.setDateOfEntry(LocalDate.now().minusDays(15));
        lap1.setLocation("Almacén Central - Estante A1");
        lap1.setCategoryId(laptopCat.getId());
        assetUseCase.createAsset(lap1);

        // 3. Crear Activo 2: Monitor y cambiarlo a ASSIGNED
        AssetRequestDTO mon1 = new AssetRequestDTO();
        mon1.setSerialNumber("SN-DELL-4K-99001");
        mon1.setModel("Dell UltraSharp 27 4K");
        mon1.setAcquisitionCost(new BigDecimal("8900.50"));
        mon1.setDateOfEntry(LocalDate.now().minusDays(30));
        mon1.setLocation("Piso 3 - Desarrollo");
        mon1.setCategoryId(monitorCat.getId());
        var savedMon = assetUseCase.createAsset(mon1);

        // Transición de estado para probar historial
        AssetHistoryRequestDTO updateMon = new AssetHistoryRequestDTO();
        updateMon.setNewStatus(AssetStatus.ASSIGNED);
        updateMon.setJustification("Asignación de equipo de trabajo por nuevo ingreso");
        updateMon.setChangedBy("admin_ti");
        updateMon.setAssignedTo("Juan Pérez - Dev Lead");
        assetUseCase.updateAssetStatus(savedMon.getId(), updateMon);

        log.info("¡Precarga de datos completada con éxito!");

    }

}
