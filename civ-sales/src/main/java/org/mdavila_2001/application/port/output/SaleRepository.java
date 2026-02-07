package org.mdavila_2001.application.port.output;

import org.mdavila_2001.domain.model.sale.Sale;

import java.util.Optional;
import java.util.UUID;

public interface SaleRepository {
    Sale save(Sale sale);
    Optional<Sale> findById(UUID id);
}
