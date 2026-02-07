package org.mdavila_2001.infrastructure.persistence.repository;

import org.mdavila_2001.infrastructure.persistence.entity.BatchJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataBatchRepository extends JpaRepository<BatchJpaEntity, UUID> {
    // Consulta optimizada para PEPS:
    // Trae solo lotes con saldo > 0, ordenados por fecha de creación (El más viejo primero)
    @Query("SELECT b FROM BatchJpaEntity b " +
            "WHERE b.product.id = :productId " +
            "AND b.branchId = :branchId " +
            "AND b.currentQuantity > 0 " +
            "ORDER BY b.createdAt ASC")
    List<BatchJpaEntity> findAvailableBatchesForFifo(
            @Param("productId") UUID productId,
            @Param("branchId") UUID branchId
    );
}
