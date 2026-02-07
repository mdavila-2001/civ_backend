package org.mdavila_2001.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sales", schema = "commercial")
@Getter @Setter
public class SaleJpaEntity {
    @Id
    private UUID id; // Asignamos el ID manual que viene del Dominio

    @Column(name = "branch_id", nullable = false)
    private UUID branchId;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String status;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    // Relación Uno-a-Muchos: Una Venta tiene muchos detalles
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id") // Esta columna estará en la tabla 'sale_details'
    private List<SaleDetailJpaEntity> items = new ArrayList<>();
}
