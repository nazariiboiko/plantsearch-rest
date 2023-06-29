package net.example.plantsearchrest.service;

import net.example.plantsearchrest.entity.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplierService {
    List<SupplierEntity> getAll();

    Page<SupplierEntity> getAll(Pageable pageable);
}
