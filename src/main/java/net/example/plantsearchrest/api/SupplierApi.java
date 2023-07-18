package net.example.plantsearchrest.api;

import io.swagger.annotations.*;
import net.example.plantsearchrest.dto.PageDto;
import net.example.plantsearchrest.dto.SupplierDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Supplier management api")
@RequestMapping("/api/v1/suppliers")
public interface SupplierApi {

    @ApiOperation("Get all suppliers")
    @ApiResponse(code = 200, message = "OK")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    PageDto<SupplierDto> getSupplierList(
            @ApiIgnore("Ignored because swagger ui shows the wrong params")
            Pageable pageable);

    @ApiOperation("Get supplier by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User found"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    SupplierDto getSupplierById(@ApiParam(value = "Supplier ID")
                                @PathVariable long id);

    @ApiOperation("Create a new supplier")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> createSupplier(
            @ApiParam(value = "Supplier DTO")
            @RequestBody SupplierDto supplierDto);

    @ApiOperation("Create a new available plant for supplier")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/plant/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> addJunction(
            @ApiParam(value = "Supplier ID")
            @PathVariable Long supplierId,
            @ApiParam(value = "Plant ID")
            @PathVariable Long plantId);

    @ApiOperation("Delete a plant for supplier")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/plant/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> deleteJunction(
            @ApiParam(value = "Supplier ID")
            @PathVariable Long supplierId,
            @ApiParam(value = "Plant ID")
            @PathVariable Long plantId);
}
