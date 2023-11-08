package net.nazariiboiko.plantsearch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
    private final SupplierService supplierService;
    private final SupplierPlantService supplierPlantService;

    @GetMapping
    public ResponseEntity<Page<SupplierDto>> getSupplierPage(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, Math.min(size, 30));
        Page<SupplierDto> dto = supplierService.getAll(pageable);
        log.info("IN getSupplierPage - return {} of suppliers", size);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getSupplierById(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        SupplierDto dto = supplierService.getById(id, pageable);
        log.info("IN getSupplierById - return {}(id:{})", dto.getName(), dto.getId());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createSupplier(@RequestBody SupplierDto supplierDto) {
        Long id = supplierService.createSupplier(supplierDto);
        log.info("IN createSupplier - supplier with id:{} has been created", id);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addPlantToSupplier(
            @RequestParam Long supplierId,
            @RequestParam Long plantId
    ) {
        supplierPlantService.create(plantId, supplierId);
        log.info("IN addPlantToSupplier - created new junction for supplier {} with plant {}", supplierId, plantId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> removePlantFromSupplier(
            @RequestParam Long supplierId,
            @RequestParam Long plantId
    ) {
        supplierPlantService.delete(plantId, supplierId);
        log.info("IN removePlantFromSupplier - junction has been dele ted for supplier {} with plant {}", supplierId, plantId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Page<SupplierDto>> findSuppliersByPlant(
            @RequestParam Long plantId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, Math.min(size, 30));
        Page<SupplierDto> dto = supplierPlantService.findByPlant(plantId, pageable);
        log.info("IN findSuppliersByPlant - return {} amount of suppliers", dto.getSize());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
