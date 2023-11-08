package net.nazariiboiko.plantsearch.service.impl;

import lombok.RequiredArgsConstructor;
import net.nazariiboiko.plantsearch.dto.SupplierDto;
import net.nazariiboiko.plantsearch.entity.SupplierPlantEntity;
import net.nazariiboiko.plantsearch.exception.PageNotFoundException;
import net.nazariiboiko.plantsearch.mapper.SupplierMapper;
import net.nazariiboiko.plantsearch.repository.SupplierPlantRepository;
import net.nazariiboiko.plantsearch.repository.SupplierRepository;
import net.nazariiboiko.plantsearch.service.SupplierPlantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SupplierPlantServiceImpl implements SupplierPlantService {

    private final SupplierRepository supRep;
    private final SupplierPlantRepository supPlantRep;
    private final SupplierMapper mapper = SupplierMapper.INSTANCE;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void create(Long plantId, Long supplierId) {
        SupplierPlantEntity entity = new SupplierPlantEntity();
        entity.setPlantId(plantId);
        entity.setSupplierId(supplierId);
        supPlantRep.save(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Long plantId, Long supplierId) {
        supPlantRep.deleteByPlantIdAndSupplierId(plantId, supplierId);
    }

    @Override
    public Page<SupplierDto> findByPlant(Long plantId, Pageable pageable) {
        Page<SupplierDto> supplierDto = supPlantRep.findByPlantId(plantId, pageable)
                .map(supplierPlantEntity -> mapper.mapEntityToDtoIgnorePlants(supRep.findById(supplierPlantEntity.getSupplierId())
                        .orElseThrow(() -> new PageNotFoundException("Supplier not found", "supplier.not_found"))));
        return supplierDto;
    }
}
