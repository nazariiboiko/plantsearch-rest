package net.example.plantsearchrest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.dto.SupplierDto;
import net.example.plantsearchrest.entity.SupplierEntity;
import net.example.plantsearchrest.mapper.SupplierMapper;
import net.example.plantsearchrest.repository.SupplierRepository;
import net.example.plantsearchrest.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepo;
    private final SupplierMapper mapper = SupplierMapper.INSTANCE;

    @Override
    public List<SupplierDto> getAll() {
        log.info("IN getAll - return all suppliers");
        List<SupplierEntity> list = supplierRepo.findAll();

        List<SupplierDto> listDto = list.stream()
                .map(mapper::mapEntityToDto)
                .collect(Collectors.toList());
        listDto.forEach(x -> x.setAvaliablePlants(null));
        return listDto;
    }

    @Override
    public SupplierDto getById(long id) {
        log.info("IN getById - return supplier id {}", id);
        SupplierEntity ent = supplierRepo.getById(id);
        return mapper.mapEntityToDto(ent);
    }

    @Override
    public SupplierDto createSupplier(SupplierDto dto) {
        SupplierEntity entity = new SupplierEntity();
        entity.setId(-1L);
        mapper.updateSupplierEntity(dto, entity);
        SupplierEntity res = supplierRepo.save(entity);
        log.info("IN createSupplier - created new instance id {}", res.getId());

        return mapper.mapEntityToDto(res);
    }

    @Override
    public void deleteSupplier(Long id) {
        supplierRepo.deleteById(id);
        log.info("IN deleteSupplier - supplier with id {} has been deleted", id);
    }
}
