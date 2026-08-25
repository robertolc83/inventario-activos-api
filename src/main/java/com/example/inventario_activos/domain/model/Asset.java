package com.example.inventario_activos.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.example.inventario_activos.domain.enums.AssetStatus;
import com.example.inventario_activos.domain.exception.asset.AssetAssidnedToNotFoundException;
import com.example.inventario_activos.domain.exception.asset.AssetNewStatusEquialToStatusException;
import com.example.inventario_activos.domain.exception.asset.AssetRetiredStatusException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String folio;

    @Column(name = "serial_number", nullable = false, unique = true, length = 50)
    private String serialNumber;

    @Column(length = 100)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AssetStatus status;

    @Column(name = "acquisition_cost", precision = 12, scale = 2)
    private BigDecimal acquisitionCost;

    @Column(name = "date_of_entry", nullable = false)
    private LocalDate dateOfEntry;

    @Column(length = 150)
    private String location;

    @Column(name = "assigned_to", length = 150)
    private String assignedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "asset")
    private List<AssetHistory> assetHistories;

    public static Asset create(
        String folio,
        String serialNumber,
        String model,
        BigDecimal acquisitionCost,
        LocalDate dateOfEntry,
        String location,
        Category category) {

        Asset asset = new Asset();
        asset.folio = Objects.requireNonNull(folio, "El folio es obligatorio");
        asset.serialNumber = Objects.requireNonNull(serialNumber, "El número de serie es obligatorio");
        asset.model = model;
        asset.status = AssetStatus.AVAILABLE;
        asset.acquisitionCost = acquisitionCost;
        asset.dateOfEntry = dateOfEntry != null ? dateOfEntry : LocalDate.now();
        asset.location = location;
        asset.category = Objects.requireNonNull(category);

        return asset;
    }

    public AssetStatus changeStatus(AssetStatus newStatus, String assignedTo) {

        if(isRetired()) {
            throw new AssetRetiredStatusException();
        }

        Objects.requireNonNull(newStatus, "El nuevo estado es obligatorio");

        if (status == newStatus) {
            throw new AssetNewStatusEquialToStatusException(status);
        }

        AssetStatus previousStatus = status;

        if (newStatus == AssetStatus.ASSIGNED) {
            if (assignedTo == null || assignedTo.isBlank()) {
                throw new AssetAssidnedToNotFoundException();
            }

            this.assignedTo = assignedTo;
        } else {
            this.assignedTo = null;
        }

        this.status = newStatus;

        return previousStatus;
    }

    public boolean isRetired() {
        return this.status == AssetStatus.RETIRED;
    }

}
