package org.mdavila_2001.infrastructure.api.dto;

import java.util.UUID;

public record SaleResponse(
        UUID saleId,
        String status,
        double totalAmount,
        String message
) {
}
