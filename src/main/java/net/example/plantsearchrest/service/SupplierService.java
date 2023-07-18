package net.example.plantsearchrest.service;

import net.example.plantsearchrest.dto.SupplierDto;
import net.example.plantsearchrest.entity.SupplierEntity;

import java.util.List;

public interface SupplierService {
    List<SupplierEntity> getAll();

    SupplierEntity getById(long id);

    SupplierEntity createSupplier(SupplierDto dto);

    void deleteSupplier(Long id);
}
