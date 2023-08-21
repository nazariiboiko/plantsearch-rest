package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.api.SupplierApi;
import net.example.plantsearchrest.dto.SupplierDto;
import net.example.plantsearchrest.exception.ServiceException;
import net.example.plantsearchrest.model.SinglePage;
import net.example.plantsearchrest.service.SupplierPlantService;
import net.example.plantsearchrest.service.SupplierService;
import net.example.plantsearchrest.utils.Messages;
import net.example.plantsearchrest.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SupplierController implements SupplierApi {

    private final SupplierService supplierService;
    private final SupplierPlantService supPlantService;
    private final Messages messages;

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
        try {
            SupplierDto dto = supplierService.createSupplier(supplierDto);
            return ResponseEntity.ok().body(dto);
        } catch (ServiceException e) {
            String message = messages.getMessage(e.getMessageCode(), new Locale("uk"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @Override
    public ResponseEntity<?> deleteSupplier(Long id) {
        try {
            supplierService.deleteSupplier(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            String message = messages.getMessage(e.getMessageCode(), new Locale("uk"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
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
        return ResponseEntity.ok(supPlantService.findByPlant(plantId));
    }
}
