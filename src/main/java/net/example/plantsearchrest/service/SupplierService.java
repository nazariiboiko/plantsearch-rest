package net.example.plantsearchrest.service;

import net.example.plantsearchrest.dto.SupplierDto;
import net.example.plantsearchrest.entity.SupplierEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplierService {
    List<SupplierDto> getAll();

    SupplierDto getById(long id, Pageable pageable);

    SupplierDto createSupplier(SupplierDto dto);

    void deleteSupplier(Long id);
}
