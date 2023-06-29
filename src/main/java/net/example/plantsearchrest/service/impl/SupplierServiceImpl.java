package net.example.plantsearchrest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.entity.SupplierEntity;
import net.example.plantsearchrest.repository.SupplierRepository;
import net.example.plantsearchrest.service.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepo;

    @Override
    public List<SupplierEntity> getAll() {
        return supplierRepo.findAll();
    }

    @Override
    public Page<SupplierEntity> getAll(Pageable pageable) {
        return supplierRepo.findAll(pageable);
    }
}
