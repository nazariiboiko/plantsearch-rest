package net.nazariiboiko.plantsearch.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.nazariiboiko.plantsearch.dto.SupplierDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Supplier", description = "Supplier API")
@RequestMapping("/api/v1/suppliers")
public interface SupplierApi {

    @Operation(summary = "Return a page of suppliers.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Page<SupplierDto>> getSupplierPage(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size
    );

    @Operation(summary = "Return a supplier by ID.")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<SupplierDto> getSupplierById(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size
    );

    @Operation(summary = "[ADMIN] Create a new supplier")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Long> createSupplier(@RequestBody SupplierDto supplierDto);

    @Operation(summary = "[ADMIN] Add a new plant to supplier")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Void> addPlantToSupplier(
            @RequestParam Long supplierId,
            @RequestParam Long plantId);

    @Operation(summary = "[ADMIN] Remove a plant from supplier")
    @PostMapping("/remove")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> removePlantFromSupplier(
            @RequestParam Long supplierId,
            @RequestParam Long plantId
    );

    @Operation(summary = "Return suppliers that include a plant")
    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<SupplierDto>> findSuppliersByPlant(
            @RequestParam Long plantId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size);
}
