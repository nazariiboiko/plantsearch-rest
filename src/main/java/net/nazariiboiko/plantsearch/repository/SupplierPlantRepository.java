package net.nazariiboiko.plantsearch.repository;

import net.nazariiboiko.plantsearch.entity.SupplierPlantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SupplierPlantRepository extends JpaRepository<SupplierPlantEntity, Long> {
    void deleteByPlantIdAndSupplierId(Long plantId, Long supplierId);
    Page<SupplierPlantEntity> findByPlantId(Long plantId, Pageable pageable);
}