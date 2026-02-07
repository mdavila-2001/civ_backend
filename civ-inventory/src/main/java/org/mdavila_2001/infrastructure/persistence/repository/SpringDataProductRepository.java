package org.mdavila_2001.infrastructure.persistence.repository;

import org.mdavila_2001.infrastructure.persistence.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataProductRepository extends JpaRepository<ProductJpaEntity, UUID> {
}
