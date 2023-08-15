package net.example.plantsearchrest.service;

import net.example.plantsearchrest.dto.SupplierDto;

import javax.transaction.Transactional;
import java.util.List;

public interface SupplierPlantService {
    @Transactional
    void create(Long plantId, Long supplierId);

    @Transactional
    void delete(Long plantId, Long supplierId);

    List<SupplierDto> findByPlant(Long plantId);
}
