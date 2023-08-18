package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.api.SupplierApi;
import net.example.plantsearchrest.dto.SupplierDto;
import net.example.plantsearchrest.model.SinglePage;
import net.example.plantsearchrest.service.SupplierPlantService;
import net.example.plantsearchrest.service.SupplierService;
import net.example.plantsearchrest.utils.PageUtil;
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
        return PageUtil.create(list, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public SupplierDto getSupplierById(long id) {
        return supplierService.getById(id);
    }

    @Override
    public ResponseEntity<?> createSupplier(SupplierDto supplierDto) {
        log.info("IN createSupplier - created a new supplier with id {}", supplierDto.getId());
        SupplierDto dto = supplierService.createSupplier(supplierDto);
        return ResponseEntity.ok().body(dto);
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
    public ResponseEntity<?> getSupplierByPlant(Long plantId) {
        log.info("IN getSupplierByPlant - finding avaliable suppliers for plant id:{}", plantId);
        return ResponseEntity.ok(supPlantService.findByPlant(plantId));
    }
}
