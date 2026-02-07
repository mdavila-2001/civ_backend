package org.mdavila_2001.domain.model.kardex.valuation;

import org.mdavila_2001.domain.model.kardex.InventoryMovement;
import org.mdavila_2001.domain.valueobject.Money;

import java.util.List;

public interface ValuationStrategy {
    Money processExit(double quantity, List<InventoryMovement> movements);

    void processEntry(double quantity, Money totalCost, List<InventoryMovement> movements);

    Money getCurrentTotalValue();

    double getCurrentStock();
}
