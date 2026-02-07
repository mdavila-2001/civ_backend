package org.mdavila_2001.infrastructure.api.dto;

import java.util.UUID;

public record CreateSaleRequest(
        UUID branchId,
        UUID productId,
        double quantity,
        double price
) {
}
