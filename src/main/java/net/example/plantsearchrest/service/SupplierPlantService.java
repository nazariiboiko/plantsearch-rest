package net.example.plantsearchrest.service;

import javax.transaction.Transactional;

public interface SupplierPlantService {
    @Transactional
    void create(Long plantId, Long supplierId);
    void delete(Long plantId, Long supplierId);
}
