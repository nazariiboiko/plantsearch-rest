package net.nazariiboiko.plantsearch.service.impl;

import lombok.RequiredArgsConstructor;
import net.nazariiboiko.plantsearch.dto.SupplierDto;
import net.nazariiboiko.plantsearch.entity.SupplierEntity;
import net.nazariiboiko.plantsearch.exception.AlreadyExistsException;
import net.nazariiboiko.plantsearch.exception.PageNotFoundException;
import net.nazariiboiko.plantsearch.mapper.SupplierMapper;
import net.nazariiboiko.plantsearch.repository.SupplierRepository;
import net.nazariiboiko.plantsearch.service.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRep;
    private final SupplierMapper mapper = SupplierMapper.INSTANCE;

    @Override
    public Page<SupplierDto> getAll(Pageable pageable) {
        Page<SupplierDto> page = supplierRep.findAll(pageable)
                .map(mapper::mapEntityToDtoIgnorePlants);
        return page;
    }

    @Override
    public SupplierDto getById(Long id, Pageable pageable) {
        SupplierEntity entity = supplierRep.findById(id).orElseThrow(() -> new PageNotFoundException("Supplier not found", "supplier_not_found"));
        return mapper.mapEntityToDto(entity, pageable);
    }

    @Override
    public Long createSupplier(SupplierDto supplierDto) {
        if(supplierRep.existsByName(supplierDto.getName()))
            throw new AlreadyExistsException("Name is already taken", "supplier.name_taken");
        SupplierEntity entityToCreate = new SupplierEntity();
        entityToCreate.setId(-1L);
        entityToCreate.setName(supplierDto.getName());
        SupplierEntity savedEntity = supplierRep.save(entityToCreate);
        return savedEntity.getId();
    }
}
