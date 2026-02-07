package org.mdavila_2001.infrastructure.api.controller;

import org.mdavila_2001.application.usecase.CreateSaleUseCase;
import org.mdavila_2001.domain.model.sale.Sale;
import org.mdavila_2001.infrastructure.api.dto.CreateSaleRequest;
import org.mdavila_2001.infrastructure.api.dto.SaleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sales")
public class SaleController {
    private final CreateSaleUseCase createSaleUseCase;

    // Spring inyectará el Caso de Uso (que configuraremos luego en el Bootstrap)
    public SaleController(CreateSaleUseCase createSaleUseCase) {
        this.createSaleUseCase = createSaleUseCase;
    }

    @PostMapping
    public ResponseEntity<SaleResponse> createSale(@RequestBody CreateSaleRequest request) {
        System.out.println("Recibiendo petición de venta para producto: " + request.productId());

        // 1. Llamar al Núcleo (Domain)
        Sale sale = createSaleUseCase.execute(
                request.branchId(),
                request.productId(),
                request.quantity(),
                request.price()
        );

        // 2. Convertir respuesta de Dominio a JSON (DTO)
        SaleResponse response = new SaleResponse(
                sale.getId(),
                sale.getStatus().toString(),
                sale.getTotalAmount().amount().doubleValue(), // Extraemos valor numérico del Money
                "Venta creada exitosamente"
        );

        return ResponseEntity.ok(response);
    }
}