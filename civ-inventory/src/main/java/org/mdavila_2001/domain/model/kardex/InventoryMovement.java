package org.mdavila_2001.domain.model.kardex;

import org.mdavila_2001.domain.valueobject.Money;

import java.time.LocalDateTime;

public record InventoryMovement(
        LocalDateTime date,
        MovementType type, // ENTRADA, SALIDA
        double quantity,
        Money unitCost,    // Costo unitario REAL de este lote
        Money totalCost,   // quantity * unitCost
        String reference   // Nro Factura o Nota de Ingreso
) {
    public enum MovementType { IN, OUT }
}
