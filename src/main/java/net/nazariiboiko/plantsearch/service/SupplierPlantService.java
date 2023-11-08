package net.nazariiboiko.plantsearch.service;

import net.nazariiboiko.plantsearch.dto.SupplierDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupplierPlantService {
    void create(Long plantId, Long SupplierId);
    void delete(Long plantId, Long SupplierId);
    Page<SupplierDto> findByPlant(Long plantId, Pageable pageable);
}
