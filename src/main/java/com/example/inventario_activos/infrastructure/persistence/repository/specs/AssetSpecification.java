package com.example.inventario_activos.infrastructure.persistence.repository.specs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.example.inventario_activos.application.dto.request.AssetFilterRequestDTO;
import com.example.inventario_activos.domain.enums.AssetStatus;
import com.example.inventario_activos.domain.model.Asset;

import jakarta.persistence.criteria.Predicate;

public class AssetSpecification {

    public static Specification<Asset> filterByParams(AssetFilterRequestDTO filter) {
        return (root, query, cb) -> {

            // Solo hace el FETCH JOIN si vamos a recuperar las entidades completas No para conteo numérico (COUNT)
            if (Long.class != query.getResultType()) {
                root.fetch("category", jakarta.persistence.criteria.JoinType.LEFT);
            }

            List<Predicate> predicates = new ArrayList<>();

            // Búsqueda por folio, número de serie o modelo (case insensitive)
            if (filter.getSearchTerm() != null && !filter.getSearchTerm().isBlank()) {
                String search = "%" + filter.getSearchTerm().toLowerCase() + "%";
                Predicate searchPredicate = cb.or(
                        cb.like(cb.lower(root.get("folio")), search),
                        cb.like(cb.lower(root.get("serialNumber")), search),
                        cb.like(cb.lower(root.get("model")), search)
                );
                predicates.add(searchPredicate);
            }

            // Filtro por Estado
            if (filter.getStatus() != null && !filter.getStatus().isBlank()) {
                predicates.add(cb.equal(root.get("status"), AssetStatus.valueOf(filter.getStatus())));
            }

            // Filtro por Categoría
            if (filter.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), filter.getCategoryId()));
            }

            // Filtro por Costo Mínimo
            if (filter.getMinCost() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("acquisitionCost"), filter.getMinCost()));
            }

            // Filtro por Costo Máximo
            if (filter.getMaxCost() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("acquisitionCost"), filter.getMaxCost()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
