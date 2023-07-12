package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.api.PlantApi;
import net.example.plantsearchrest.dto.PageDto;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.mapper.PlantMapper;
import net.example.plantsearchrest.model.PlantFilterModel;
import net.example.plantsearchrest.service.PlantService;
import net.example.plantsearchrest.utils.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlantController implements PlantApi {

    private final PlantService plantService;
    private final PlantMapper plantMapper = PlantMapper.INSTANCE;

    @Override
    public PageDto<PlantDto> getPlantList(Pageable pageable) {
        log.info("IN getPlantList | return page {} with size {} objects", pageable.getPageNumber(), pageable.getPageSize());

        List<PlantDto> list = plantService.getAll().stream()
                .map(plantMapper::mapEntityToDto)
                .collect(Collectors.toList());
        return PageUtil.create(list, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public PlantDto getPlantById(long id) {
        log.info("IN plantByIndex | return object with {} id", id);
        return plantMapper.mapEntityToDto(plantService.getById(id));
    }

    @Override
    public List<PlantDto> getRandomPlantList(int amount) {

        log.info("IN plantListRandom | return {} objects", amount);
        if(amount < 1)
            amount = 1;
        return plantService.getRandom(amount).stream()
                .map(plantMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PageDto<PlantDto> searchPlantsByName(String keyword, Pageable pageable) {
        List<PlantDto> list = plantService.findByMatchingName(keyword).stream()
                .map(plantMapper::mapEntityToDto)
                .collect(Collectors.toList());

        log.info("IN searchSimilarByName | return {} objects for {} query", list.size(), keyword);
        return PageUtil.create(list, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public PageDto<PlantDto> filterPlants(PlantFilterModel filter, Pageable pageable) {

        log.info("IN filterPlants - received filter criterias {}" + filter.toString());

        List<PlantDto> list = plantService.getAllByCriterias(filter).stream()
                .map(plantMapper::mapEntityToDto)
                .collect(Collectors.toList());


        return PageUtil.create(list, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public ResponseEntity createPlant(PlantDto plantDto) {
        log.info("IN createPlant - created new instance");
        PlantEntity entity = plantService.create(plantDto);
        return ResponseEntity.ok(entity.getId());
    }

    @Override
    public ResponseEntity updatePlant(PlantDto plantDto) {
        log.info("IN updatePlant - trying to update plant id {}", plantDto.getId());
        plantService.update(plantDto);
        return ResponseEntity.ok(plantDto.getId());

    }

    @Override
    public ResponseEntity deletePlant(long id) {
        log.info("IN deletePlant - trying to delete plant id {}", id);
        plantService.delete(id);

        return ResponseEntity.ok(null);
    }
}
