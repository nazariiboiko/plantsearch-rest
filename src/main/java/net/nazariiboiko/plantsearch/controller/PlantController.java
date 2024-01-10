package net.nazariiboiko.plantsearch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nazariiboiko.plantsearch.api.PlantApi;
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
public class PlantController implements PlantApi {

    private final PlantService plantService;

   @Override
    public ResponseEntity<Page<PlantPreviewDto>> getAllPlants(int page, int size) {
        Pageable pageable = PageRequest.of(page, Math.min(50, size));
        Page<PlantPreviewDto> dto = plantService.getAllPlants(pageable);
        log.info("IN getAllPlants - Returned page {} of plants", page);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<PlantDto> getInfoAboutPlant(Long id) {
        PlantDto dto = plantService.getPlant(id);
        log.info("IN getInfoAboutPlant - return plant with id:{}", id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<List<PlantPreviewDto>> getRandomPlants(int amount) {
        List<PlantPreviewDto> dtoList = plantService.getRandomPlants(Math.min(30, amount));
        log.info("IN getRandomPlants - returned {} plants", amount);
        return ResponseEntity.ok(dtoList);
    }

    @Override
    public ResponseEntity<List<PlantPreviewDto>> getPlantsByNameForAutocomplete(String name ) {
        List<PlantPreviewDto> dtoList = plantService.getPlantsByName(name, 4);
        log.info("IN getPlantByNameForAutocomplete - searching for name: {}", name);
        return ResponseEntity.ok(dtoList);
    }

    @Override
    public ResponseEntity<Page<PlantPreviewDto>> getPlantsByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page,  Math.min(50, size));
        Page<PlantPreviewDto> dtoList = plantService.getPlantsByName(name, pageable);
        log.info("IN getPlantsByName - searching for name: {}", name);
        return ResponseEntity.ok(dtoList);
    }

    @Override
    public ResponseEntity<Page<PlantPreviewDto>> filterPlants(int page, int size, PlantFilterModel filter) {
        log.info("IN filterPlants - filtering plants with criteria:{}", filter.toString());
        Pageable pageable = PageRequest.of(page, Math.min(50, size));
        Page<PlantPreviewDto> dtoList = plantService.getPlantsByFilter(filter, pageable);
        return ResponseEntity.ok(dtoList);
    }

    @Override
    public ResponseEntity<Long> createPlant(PlantDto plantDto, MultipartFile image, MultipartFile sketch) {
        long id = plantService.createPlant(plantDto, image, sketch);
        log.info("IN createPlant - plant(id:{}) has been created", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @Override
    public ResponseEntity<Void> updatePlant(PlantDto plantDto, MultipartFile image, MultipartFile sketch) {
        plantService.update(plantDto, image, sketch);
        log.info("IN updatePlant - plant(id:{}) has been updated", plantDto.getId());
        return ResponseEntity.ok().build();
    }
}
