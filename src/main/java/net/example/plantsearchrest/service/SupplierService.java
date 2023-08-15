package net.example.plantsearchrest.service;

import net.example.plantsearchrest.dto.SupplierDto;
import net.example.plantsearchrest.entity.SupplierEntity;

import java.util.List;

public interface SupplierService {
    List<SupplierDto> getAll();

    SupplierDto getById(long id);

    SupplierDto createSupplier(SupplierDto dto);

    void deleteSupplier(Long id);
}
