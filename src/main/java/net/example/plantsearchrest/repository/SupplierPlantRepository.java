package net.example.plantsearchrest.repository;

import net.example.plantsearchrest.entity.SupplierPlantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierPlantRepository extends JpaRepository<SupplierPlantEntity, Long> {
    void deleteByPlantIdAndSupplierId(Long plantId, Long supplierId);

}
