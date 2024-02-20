package net.nazariiboiko.plantsearch.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.nazariiboiko.plantsearch.dto.PlantMarkerGroupDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/markers")
@Tag(name = "Plant Marker", description = "Image with different plants.")
public interface PlantMarkerApi {

    @Operation(summary = "Get a page of all images")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Page<PlantMarkerGroupDto>> getAllPlantMarkerGroups(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size);

    @Operation(summary = "Get Plant Marker Group by ID")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found marker group by Id"),
            @ApiResponse(responseCode = "404", description = "Marker group not found")
    })
    ResponseEntity<PlantMarkerGroupDto> getPlantMarkerGroup(@PathVariable Long id);

    @Operation(summary = "Add Plant Marker Group")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Long> addPlantMarkerGroup(@RequestPart PlantMarkerGroupDto groupDto, @RequestPart MultipartFile image);

    @Operation(summary = "Update Plant Marker Group")
    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Long> updatePlantMarkerGroup(@RequestPart PlantMarkerGroupDto groupDto, @RequestPart(required = false) MultipartFile image);

    @Operation(summary = "Delete Plant Marker Group")
    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Void> deletePlantMarkerGroup(@RequestParam Long groupId);

    @Operation(summary = "Get all plant markers for a plant")
    @GetMapping("/plant/{plantId}")
    ResponseEntity<Page<PlantMarkerGroupDto>> getAllPlantMarkersForPlant(@PathVariable Long plantId, @RequestParam int page, @RequestParam int size);

}