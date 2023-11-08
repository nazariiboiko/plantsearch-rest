package net.nazariiboiko.plantsearch.repository;

import net.nazariiboiko.plantsearch.entity.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
    Page<SupplierEntity> findAll(Pageable pageable);
    SupplierEntity findByName(String name);
    boolean existsByName(String name);

}
