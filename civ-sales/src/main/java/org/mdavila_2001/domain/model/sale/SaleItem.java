package org.mdavila_2001.domain.model.sale;

import org.mdavila_2001.domain.valueobject.Money;

import java.util.UUID;

public class SaleItem {
    private final UUID productId;
    private final String productName; // Guardamos el nombre al momento de la venta (snapshot)
    private final double quantity;
    private final Money unitPrice;
    private final Money subtotal;

    public SaleItem(UUID productId, String productName, double quantity, Money unitPrice) {
        if (quantity <= 0) throw new IllegalArgumentException("La cantidad debe ser positiva");

        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = unitPrice.multiply(quantity);
    }

    public Money getSubtotal() {
        return subtotal;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getQuantity() {
        return quantity;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }
}
