package org.mdavila_2001.infrastructure.adapter;

import org.mdavila_2001.domain.model.product.Product;
import org.mdavila_2001.infrastructure.persistence.entity.ProductJpaEntity;
import org.mdavila_2001.infrastructure.persistence.repository.SpringDataProductRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PostgresInventoryAdapter {
    private final SpringDataProductRepository productRepo;

    public PostgresInventoryAdapter(SpringDataProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public void save(Product domainProduct) {
        // 1. Convertir Domain -> Entity
        ProductJpaEntity entity = mapToEntity(domainProduct);
        // 2. Guardar
        productRepo.save(entity);
        // 3. (Opcional) Actualizar ID en dominio si era nuevo
    }

    public Optional<Product> findById(UUID id) {
        return productRepo.findById(id).map(this::mapToDomain);
    }

    // Mappers simples (Podríamos usar MapStruct más adelante)
    private ProductJpaEntity mapToEntity(Product domain) {
        ProductJpaEntity entity = new ProductJpaEntity();
        entity.setId(domain.getId());
        entity.setSku(domain.getSku());
        entity.setValuation(domain.getValuationMethod());
        // ... mapear resto de campos
        return entity;
    }

    private Product mapToDomain(ProductJpaEntity entity) {
        return new Product(
                entity.getSku(),
                entity.getName(),
                entity.getValuation()
        );
        // Aquí deberíamos inyectar la estrategia PEPS cargando los lotes si fuera necesario
    }
}