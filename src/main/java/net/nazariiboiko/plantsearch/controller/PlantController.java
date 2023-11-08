package net.nazariiboiko.plantsearch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nazariiboiko.plantsearch.dto.PlantDto;
import net.nazariiboiko.plantsearch.dto.PlantPreviewDto;
import net.nazariiboiko.plantsearch.model.PlantFilterModel;
import net.nazariiboiko.plantsearch.service.PlantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plants")
public class PlantController {

    private final PlantService plantService;

    @GetMapping
    public ResponseEntity<Page<PlantPreviewDto>> getAllPlants(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, Math.min(50, size));
        Page<PlantPreviewDto> dto = plantService.getAllPlants(pageable);
        log.info("IN getAllPlants - Returned page {} of plants", page);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantDto> getInfoAboutPlant(@PathVariable Long id) {
        PlantDto dto = plantService.getPlant(id);
        log.info("IN getInfoAboutPlant - return plant with id:{}", id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/random")
    public ResponseEntity<List<PlantPreviewDto>> getRandomPlants(
            @RequestParam(required = false, defaultValue = "1") int amount) {
        List<PlantPreviewDto> dtoList = plantService.getRandomPlants(Math.min(30, amount));
        log.info("IN getRandomPlants - returned {} plants", amount);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<PlantPreviewDto>> getPlantsByNameForAutocomplete(
            @RequestParam String name ) {
        List<PlantPreviewDto> dtoList = plantService.getPlantsByName(name, 4);
        log.info("IN getPlantByNameForAutocomplete - searching for name: {}", name);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PlantPreviewDto>> getPlantsByName(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page,  Math.min(50, size));
        Page<PlantPreviewDto> dtoList = plantService.getPlantsByName(name, pageable);
        log.info("IN getPlantsByName - searching for name: {}", name);
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<PlantPreviewDto>> filterPlants(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestBody PlantFilterModel filter) {
        log.info("IN filterPlants - filtering plants with criteria:{}", filter.toString());
        Pageable pageable = PageRequest.of(page, Math.min(50, size));
        Page<PlantPreviewDto> dtoList = plantService.getPlantsByFilter(filter, pageable);
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> createPlant(
            @RequestPart PlantDto plantDto,
            @RequestPart(name = "image", required = false) MultipartFile image,
            @RequestPart(name = "sketch", required = false) MultipartFile sketch) {
        long id = plantService.createPlant(plantDto, image, sketch);
        log.info("IN createPlant - plant(id:{}) has been created", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updatePlant(
            @RequestPart PlantDto plantDto,
            @RequestPart(name = "image", required = false) MultipartFile image,
            @RequestPart(name = "sketch", required = false) MultipartFile sketch) {
        plantService.update(plantDto, image, sketch);
        log.info("IN updatePlant - plant(id:{}) has been updated", plantDto.getId());
        return ResponseEntity.ok().build();
    }
}
