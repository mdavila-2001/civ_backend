package org.mdavila_2001.application.port.output;

import java.util.UUID;

public interface InventoryPort {
    boolean checkStock(UUID productId, double quantity);
    void reduceStock(UUID productId, double quantity);
}
