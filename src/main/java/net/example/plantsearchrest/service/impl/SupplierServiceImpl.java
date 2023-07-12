package net.example.plantsearchrest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.entity.SupplierEntity;
import net.example.plantsearchrest.repository.SupplierRepository;
import net.example.plantsearchrest.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepo;

    @Override
    public List<SupplierEntity> getAll() {
        log.info("IN getAll - return all suppliers");
        return supplierRepo.findAll();
    }

    @Override
    public SupplierEntity getById(long id) {
        log.info("IN getById - return supplier id {}", id);
        return supplierRepo.getById(id);
    }
}
