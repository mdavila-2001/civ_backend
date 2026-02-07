package org.mdavila_2001.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "sale_details", schema = "commercial")
@Getter @Setter
public class SaleDetailJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // No usamos @ManyToOne hacia Sale aquí para evitar ciclos infinitos en JSON/Lombok,
    // dejaremos que Sale maneje la relación.

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal subtotal;
}