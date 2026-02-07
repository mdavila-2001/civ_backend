package org.mdavila_2001.domain.model.sale;

import org.mdavila_2001.domain.valueobject.Money;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Sale {
    private final UUID id;
    private final UUID branchId;
    private final LocalDateTime date;
    private SaleStatus status;
    private final List<SaleItem> items;
    private Money totalAmount;

    public enum SaleStatus { DRAFT, CONFIRMED, ANNULLED }

    public Sale(UUID branchId) {
        this.id = UUID.randomUUID();
        this.branchId = branchId;
        this.date = LocalDateTime.now();
        this.status = SaleStatus.DRAFT;
        this.items = new ArrayList<>();
        this.totalAmount = Money.bob(0);
    }

    public void addItem(UUID productId, String name, double quantity, Money price) {
        if (this.status != SaleStatus.DRAFT) throw new IllegalStateException("Venta cerrada");

        SaleItem item = new SaleItem(productId, name, quantity, price);
        this.items.add(item);
        recalculateTotal();
    }

    public void confirm() {
        if (items.isEmpty()) throw new IllegalStateException("Venta vacía");
        this.status = SaleStatus.CONFIRMED;
    }

    private void recalculateTotal() {
        this.totalAmount = items.stream()
                .map(SaleItem::getSubtotal)
                .reduce(Money.bob(0), Money::add);
    }

    // --- GETTERS NECESARIOS PARA PERSISTENCIA ---
    public UUID getId() { return id; }
    public UUID getBranchId() { return branchId; }
    public LocalDateTime getDate() { return date; }
    public SaleStatus getStatus() { return status; }
    public Money getTotalAmount() { return totalAmount; }
    public List<SaleItem> getItems() { return Collections.unmodifiableList(items); }
}
