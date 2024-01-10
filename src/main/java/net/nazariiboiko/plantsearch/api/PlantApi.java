package net.nazariiboiko.plantsearch.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.nazariiboiko.plantsearch.dto.PlantDto;
import net.nazariiboiko.plantsearch.dto.PlantPreviewDto;
import net.nazariiboiko.plantsearch.model.PlantFilterModel;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Plant", description = "API for plants management")
@RequestMapping("/api/v1/plants")
public interface PlantApi {

    @Operation(summary = "Get a page of plants.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Page<PlantPreviewDto>> getAllPlants(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size);

    @Operation(summary = "Get a plant by ID.")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PlantDto> getInfoAboutPlant(@PathVariable Long id);


    @Operation(summary = "Get a random amount of plants.")
    @GetMapping("/random")
    ResponseEntity<List<PlantPreviewDto>> getRandomPlants(
            @RequestParam(required = false, defaultValue = "1") int amount);

    @Operation(summary = "Get a list of plants by keyword.")
    @GetMapping("/autocomplete")
    ResponseEntity<List<PlantPreviewDto>> getPlantsByNameForAutocomplete(
            @RequestParam String name );

    @Operation(summary = "Get a list of plants by name.")
    @GetMapping("/search")
    ResponseEntity<Page<PlantPreviewDto>> getPlantsByName(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size);

    @Operation(summary = "Get a list of plants by filter model.")
    @PostMapping("/filter")
    ResponseEntity<Page<PlantPreviewDto>> filterPlants(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestBody PlantFilterModel filter);

    @Operation(summary = "[ADMIN] Create a new plant")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Long> createPlant(
            @RequestPart PlantDto plantDto,
            @RequestPart(name = "image", required = false) MultipartFile image,
            @RequestPart(name = "sketch", required = false) MultipartFile sketch);


    @Operation(summary = "[ADMIN] Update plant")
    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Void> updatePlant(
            @RequestPart PlantDto plantDto,
            @RequestPart(name = "image", required = false) MultipartFile image,
            @RequestPart(name = "sketch", required = false) MultipartFile sketch);
}
