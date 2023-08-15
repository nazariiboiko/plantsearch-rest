package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.api.PlantApi;
import net.example.plantsearchrest.model.SinglePage;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.mapper.PlantMapper;
import net.example.plantsearchrest.model.PlantFilterModel;
import net.example.plantsearchrest.service.PlantService;
import net.example.plantsearchrest.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlantController implements PlantApi {

    private final PlantService plantService;

    @Override
    public SinglePage<PlantDto> getPlantList(Pageable pageable) {
        List<PlantDto> list = plantService.getAll();
        return PageUtil.create(list, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public PlantDto getPlantById(long id) {
        return plantService.getById(id);
    }

    @Override
    public List<PlantDto> getRandomPlantList(int amount) {
        return plantService.getRandom(amount);
    }

    @Override
    public SinglePage<PlantDto> searchPlantsByName(String keyword, Pageable pageable) {
        List<PlantDto> list = plantService.findByMatchingName(keyword);

        log.info("IN searchSimilarByName | return {} objects for {} query", list.size(), keyword);
        return PageUtil.create(list, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public SinglePage<PlantDto> filterPlants(PlantFilterModel filter, Pageable pageable) {
        log.info("IN filterPlants - received filter criterias {}", filter.toString());

        List<PlantDto> list = plantService.getAllByCriterias(filter);

        return PageUtil.create(list, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public ResponseEntity createPlant(PlantDto plantDto, MultipartFile image, MultipartFile sketch) throws IOException {
        log.info("IN createPlant - created new instance");
        PlantEntity entity = plantService.create(plantDto, image, sketch);
        return ResponseEntity.ok(entity.getId());
    }

    @Override
    public ResponseEntity<Long> updatePlant(PlantDto plantDto, MultipartFile image, MultipartFile sketch) throws IOException {
        log.info("IN updatePlant - trying to update plant id {}", plantDto.getId());
        plantService.update(plantDto, image, sketch);
        return ResponseEntity.ok(plantDto.getId());

    }

    @Override
    public ResponseEntity deletePlant(long id) {
        log.info("IN deletePlant - trying to delete plant id {}", id);
        plantService.delete(id);

        return ResponseEntity.ok(null);
    }
}
