package org.mdavila_2001.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mdavila_2001.domain.model.product.ValuationMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products", schema = "logistics")
@Getter @Setter
public class ProductJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(name = "unit_measure")
    private String unitMeasure;

    @Enumerated(EnumType.STRING) // Guarda "PEPS" o "PPP" como texto
    @Column(nullable = false)
    private ValuationMethod valuation;

    @Column(name = "min_stock")
    private BigDecimal minStock;

    // 🔒 BLOQUEO OPTIMISTA: Evita que dos usuarios editen el producto al mismo tiempo
    @Version
    private Long version;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
