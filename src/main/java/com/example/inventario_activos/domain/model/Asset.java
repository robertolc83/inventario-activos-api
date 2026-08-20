package com.example.inventario_activos.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.example.inventario_activos.domain.enums.AssetStatus;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

}
