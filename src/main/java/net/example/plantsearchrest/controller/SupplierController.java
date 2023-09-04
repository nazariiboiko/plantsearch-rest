package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.api.SupplierApi;
import net.example.plantsearchrest.dto.SupplierDto;
import net.example.plantsearchrest.exception.ServiceException;
import net.example.plantsearchrest.model.SinglePage;
import net.example.plantsearchrest.service.SupplierPlantService;
import net.example.plantsearchrest.service.SupplierService;
import net.example.plantsearchrest.utils.PageUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SupplierController implements SupplierApi {

    private final SupplierService supplierService;
    private final SupplierPlantService supPlantService;

    @Override
    public SinglePage<SupplierDto> getSupplierList(Pageable pageable) {
        List<SupplierDto> list = supplierService.getAll();
        log.info("IN getSupplierList - return {} of suppliers", list.size());
        return PageUtil.create(list, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public SupplierDto getSupplierById(long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        SupplierDto dto = supplierService.getById(id, pageable);
        log.info("IN getSupplierById - return {}(id:{})", dto.getName(), dto.getId());
        return dto;
    }

    @Override
    public ResponseEntity<SupplierDto> createSupplier(SupplierDto supplierDto) throws ServiceException {
        SupplierDto dto = supplierService.createSupplier(supplierDto);
        log.info("IN createSupplier - supplier with id:{} has been created", dto.getId());
        return ResponseEntity.ok().body(dto);
    }

    @Override
    public ResponseEntity<?> deleteSupplier(Long id) throws ServiceException {
        supplierService.deleteSupplier(id);
        log.info("IN deleteSupplier - supplier with id:{} has been deleted", id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> addJunction(Long supplierId, Long plantId) {
        log.info("IN addJunction - creating new junction for supplier {} with plant {}", supplierId, plantId);
        supPlantService.create(plantId, supplierId);
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<?> deleteJunction(Long supplierId, Long plantId) {
        log.info("IN deleteJunction - deleting junction for supplier {} with plant {}", supplierId, plantId);
        supPlantService.delete(plantId, supplierId);
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<List<SupplierDto>> getSupplierByPlant(Long plantId) {
        List<SupplierDto> dtos = supPlantService.findByPlant(plantId);
        log.info("IN getSupplierByPlant - return {} suppliers", dtos.size());
        return ResponseEntity.ok(dtos);
    }
}
