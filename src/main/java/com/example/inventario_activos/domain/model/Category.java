package com.example.inventario_activos.domain.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 3)
    private String code;

    @OneToMany(
        mappedBy = "category")
    private List<Asset> assets;

    public static Category create(String name, String code) {

        Category category = new Category();
        category.name = Objects.requireNonNull(name, "El nombre es obligatorio");
        category.code = Objects.requireNonNull(code, "El código es obligatorio");

        return category;
    }

}
