package net.example.plantsearchrest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.entity.SupplierPlantEntity;
import net.example.plantsearchrest.repository.SupplierPlantRepository;
import net.example.plantsearchrest.service.SupplierPlantService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierPlantServiceImpl implements SupplierPlantService {

    SupplierPlantRepository supPlantRep;

    @Override
    public void create(Long plantId, Long supplierId) {
        SupplierPlantEntity entity = new SupplierPlantEntity();
        entity.setPlantId(plantId);
        entity.setSupplierId(supplierId);
        supPlantRep.save(entity);
    }

    @Override
    public void delete(Long plantId, Long supplierId) {
        supPlantRep.deleteByPlantIdAndSupplierId(plantId, supplierId);
    }
}
