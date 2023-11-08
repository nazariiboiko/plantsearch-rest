package net.nazariiboiko.plantsearch.service;

import net.nazariiboiko.plantsearch.dto.SupplierDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupplierService {
    Page<SupplierDto> getAll(Pageable pageable);

    SupplierDto getById(Long id, Pageable pageable);

    Long createSupplier(SupplierDto supplierDto);
}
