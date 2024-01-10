package net.nazariiboiko.plantsearch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nazariiboiko.plantsearch.api.SupplierApi;
import net.nazariiboiko.plantsearch.dto.SupplierDto;
import net.nazariiboiko.plantsearch.service.SupplierPlantService;
import net.nazariiboiko.plantsearch.service.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SupplierController implements SupplierApi {
    private final SupplierService supplierService;
    private final SupplierPlantService supplierPlantService;

    @Override
    public ResponseEntity<Page<SupplierDto>> getSupplierPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, Math.min(size, 30));
        Page<SupplierDto> dto = supplierService.getAll(pageable);
        log.info("IN getSupplierPage - return {} of suppliers", size);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SupplierDto> getSupplierById(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        SupplierDto dto = supplierService.getById(id, pageable);
        log.info("IN getSupplierById - return {}(id:{})", dto.getName(), dto.getId());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Long> createSupplier(SupplierDto supplierDto) {
        Long id = supplierService.createSupplier(supplierDto);
        log.info("IN createSupplier - supplier with id:{} has been created", id);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> addPlantToSupplier(Long supplierId, Long plantId
    ) {
        supplierPlantService.create(plantId, supplierId);
        log.info("IN addPlantToSupplier - created new junction for supplier {} with plant {}", supplierId, plantId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> removePlantFromSupplier(Long supplierId, Long plantId
    ) {
        supplierPlantService.delete(plantId, supplierId);
        log.info("IN removePlantFromSupplier - junction has been deleted for supplier {} with plant {}", supplierId, plantId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<SupplierDto>> findSuppliersByPlant(Long plantId, int page, int size) {
        Pageable pageable = PageRequest.of(page, Math.min(size, 30));
        Page<SupplierDto> dto = supplierPlantService.findByPlant(plantId, pageable);
        log.info("IN findSuppliersByPlant - return {} amount of suppliers", dto.getSize());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
