package org.mdavila_2001.domain.model.kardex;

import org.mdavila_2001.domain.model.product.Product;
import org.mdavila_2001.domain.valueobject.Money;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Kardex {
    private final UUID id;
    private final Product product;
    private final String warehouseId;

    private double currentStock;
    private Money currentAverageCost;

    private final List<InventoryMovement> movements;

    public Kardex(Product product, String warehouseId) {
        this.id = UUID.randomUUID();
        this.product = product;
        this.warehouseId = warehouseId;
        this.movements = new ArrayList<>();
        this.currentStock = 0.0;
        this.currentAverageCost = Money.zero("BOB"); // Inicializar según moneda del producto
    }

    public void registerEntry(double quantity, Money totalCost, String reference) {
        if (quantity <= 0) throw new IllegalArgumentException("La cantidad de entrada debe ser positiva");

        Money unitCost = totalCost.divide(quantity);

        // 1. Agregar Movimiento
        InventoryMovement move = new InventoryMovement(
                LocalDateTime.now(),
                InventoryMovement.MovementType.IN,
                quantity,
                unitCost,
                totalCost,
                reference
        );
        this.movements.add(move);

        // 2. Recalcular Promedio Ponderado (Si aplica)
        // Nuevo Costo Promedio = (CostoTotalActual + CostoNuevoIngreso) / (StockActual + CantidadNueva)
        Money currentTotalValue = this.currentAverageCost.multiply(this.currentStock);
        Money newTotalValue = currentTotalValue.add(totalCost);
        double newTotalStock = this.currentStock + quantity;

        this.currentAverageCost = newTotalValue.divide(newTotalStock);
        this.currentStock = newTotalStock;
    }

    /**
     * Registra una VENTA (Salida)
     * Regla de Negocio: Sale al costo promedio actual (si es PP).
     */
    public void registerExit(double quantity, String reference) {
        if (quantity > this.currentStock) {
            throw new IllegalStateException("Stock insuficiente. Disponible: " + currentStock);
        }

        // En PP, el costo de salida es el costo promedio actual
        Money costOfExit = this.currentAverageCost.multiply(quantity);

        InventoryMovement move = new InventoryMovement(
                LocalDateTime.now(),
                InventoryMovement.MovementType.OUT,
                quantity,
                this.currentAverageCost, // Sale valorado al promedio
                costOfExit,
                reference
        );
        this.movements.add(move);

        this.currentStock -= quantity;
    }

    public double getCurrentStock() {
        return currentStock;
    }

    public Money getCurrentTotalValue() {
        return currentAverageCost.multiply(currentStock);
    }
}
