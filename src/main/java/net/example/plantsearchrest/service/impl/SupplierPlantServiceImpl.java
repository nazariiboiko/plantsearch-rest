package net.example.plantsearchrest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.dto.SupplierDto;
import net.example.plantsearchrest.entity.SupplierPlantEntity;
import net.example.plantsearchrest.mapper.SupplierMapper;
import net.example.plantsearchrest.repository.SupplierPlantRepository;
import net.example.plantsearchrest.repository.SupplierRepository;
import net.example.plantsearchrest.service.SupplierPlantService;
import net.example.plantsearchrest.service.SupplierService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierPlantServiceImpl implements SupplierPlantService {

    private final SupplierPlantRepository supPlantRep;
    private final SupplierRepository supRep;
    private final SupplierMapper mapper = SupplierMapper.INSTANCE;

    @Override
    @Transactional
    public void create(Long plantId, Long supplierId) {
        SupplierPlantEntity entity = new SupplierPlantEntity();
        entity.setPlantId(plantId);
        entity.setSupplierId(supplierId);
        supPlantRep.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long plantId, Long supplierId) {
        supPlantRep.deleteByPlantIdAndSupplierId(plantId, supplierId);
    }

    @Override
    public List<SupplierDto> findByPlant(Long plantId) {
        List<SupplierPlantEntity> list = supPlantRep.findByPlantId(plantId);

        List<SupplierDto> dto = list.stream()
                .map(x -> mapper.mapEntityToDto(supRep.getById(x.getSupplierId())))
                .collect(Collectors.toList());
        dto.stream().forEach(x -> x.setAvaliablePlants(null));

        return dto;
    }
}
