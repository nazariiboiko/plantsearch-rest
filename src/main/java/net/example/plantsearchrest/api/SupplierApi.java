package net.example.plantsearchrest.api;

import io.swagger.annotations.*;
import net.example.plantsearchrest.dto.PageDto;
import net.example.plantsearchrest.dto.SupplierDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "Supplier management api")
@RequestMapping("/api/v1/suppliers")
public interface SupplierApi {

    @ApiOperation("Get all suppliers")
    @ApiResponse(code = 200, message = "OK")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    SupplierDto getSupplierById(@ApiParam(value = "Supplier ID")
                                @PathVariable long id);
}
