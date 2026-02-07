package org.mdavila_2001.domain.model.product;

import java.util.UUID;

public class Product {
    private final UUID id;
    private String sku;
    private String name;
    private String description;
    private ValuationMethod valuationMethod;

    public Product(String sku, String name, ValuationMethod valuationMethod) {
        this.id = UUID.randomUUID();
        this.sku = sku;
        this.name = name;
        this.valuationMethod = valuationMethod;
    }

    public UUID getId() { return id; }
    public ValuationMethod getValuationMethod() { return valuationMethod; }
    public String getSku() { return sku; }

    public void changeValuationMethod(ValuationMethod newMethod) {
        // Aquí podríamos validar si se permite cambiar el método si ya hay stock (regla de negocio estricta)
        this.valuationMethod = newMethod;
    }
}
