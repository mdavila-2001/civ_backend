package org.mdavila_2001.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "batches", schema = "logistics")
@Getter @Setter
public class BatchJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Relación Lazy para no traer el producto entero si no se necesita
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductJpaEntity product;

    @Column(name = "branch_id", nullable = false)
    private UUID branchId;

    @Column(name = "batch_code")
    private String batchCode;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    // Dinero: Mapeamos directo a BigDecimal.
    // El Mapper del Dominio se encargará de convertirlo a "Money.bob(val)"
    @Column(name = "cost_unit", nullable = false)
    private BigDecimal costUnit;

    @Column(name = "initial_quantity", nullable = false)
    private BigDecimal initialQuantity;

    @Column(name = "current_quantity", nullable = false)
    private BigDecimal currentQuantity;

    // 🔒 BLOQUEO OPTIMISTA: Vital para PEPS
    // Si dos ventas intentan consumir el mismo lote exacto al mismo tiempo,
    // una fallará y podremos reintentar automáticamente.
    @Version
    private Long version;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
