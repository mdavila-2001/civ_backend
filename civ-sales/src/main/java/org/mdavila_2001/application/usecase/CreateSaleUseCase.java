package org.mdavila_2001.application.usecase;

import org.mdavila_2001.application.port.output.InventoryPort;
import org.mdavila_2001.application.port.output.SaleRepository;
import org.mdavila_2001.domain.model.sale.Sale;
import org.mdavila_2001.domain.valueobject.Money;

import java.util.UUID;

public class CreateSaleUseCase {
    private final InventoryPort inventoryPort;
    private final SaleRepository saleRepository; // <--- Dependencia nueva

    public CreateSaleUseCase(InventoryPort inventoryPort, SaleRepository saleRepository) {
        this.inventoryPort = inventoryPort;
        this.saleRepository = saleRepository;
    }

    public Sale execute(UUID branchId, UUID productId, double quantity, double priceDto) {
        // 1. Validar Stock
        boolean hasStock = inventoryPort.checkStock(productId, quantity);
        if (!hasStock) throw new RuntimeException("Sin stock suficiente");

        // 2. Crear Venta
        Sale sale = new Sale(branchId);
        sale.addItem(productId, "Producto Ejemplo", quantity, Money.bob(priceDto));

        // 3. Confirmar Lógica de Negocio
        sale.confirm();

        // 4. Actualizar Inventario
        inventoryPort.reduceStock(productId, quantity);

        // 5. GUARDAR (Persistencia)
        saleRepository.save(sale);

        return sale;
    }
}
