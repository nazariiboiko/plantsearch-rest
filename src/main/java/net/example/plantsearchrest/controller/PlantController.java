package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.api.PlantApi;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.dto.PlantPreviewDto;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.exception.NotFoundException;
import net.example.plantsearchrest.model.PlantFilterModel;
import net.example.plantsearchrest.model.SinglePage;
import net.example.plantsearchrest.security.jwt.JwtUser;
import net.example.plantsearchrest.service.PlantService;
import net.example.plantsearchrest.utils.PageUtil;
import net.example.plantsearchrest.utils.UserUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlantController implements PlantApi {

    private final PlantService plantService;

    @Override
    public SinglePage<PlantPreviewDto> getPlantList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<PlantPreviewDto> list = plantService.getAll();
        log.info("IN getPlantList - return Page({},{}) for all plants", page, size);
        return PageUtil.create(list, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public PlantDto getPlantById(long id) throws NotFoundException {
        log.info("IN getPlantById - return plant id:{}",id);
        return plantService.getById(id);
    }

    @Override
    public List<PlantPreviewDto> getRandomPlantList(int amount) {
        log.info("IN getRandomPlantList - return {} random plants", amount);
        return plantService.getRandom(amount);
    }

    @Override
    public SinglePage<PlantPreviewDto> searchPlantsByName(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<PlantPreviewDto> list = plantService.findByMatchingName(keyword);

        log.info("IN searchPlantByName - return {} similar plants for keyword: {}", list.size(), keyword);
        return PageUtil.create(list, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public SinglePage<PlantPreviewDto> filterPlants(PlantFilterModel filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<PlantPreviewDto> list = plantService.getAllByCriterias(filter);

        log.info("IN filterPlants - return {} plants", list.size());
        return PageUtil.create(list, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public ResponseEntity<Long> createPlant(PlantDto plantDto, MultipartFile image, MultipartFile sketch) throws IOException {
        PlantEntity entity = plantService.create(plantDto, image, sketch);
        log.info("IN createPlant - created {}(id:{})", entity.getId(), entity.getLatinName());
        return ResponseEntity.ok(entity.getId());
    }

    @Override
    public ResponseEntity<Long> updatePlant(PlantDto plantDto, MultipartFile image, MultipartFile sketch) throws IOException {
        JwtUser user = UserUtil.getAuthUser();
        log.info("IN updatePlant - User {}(id:{}) is trying to update plant {}(id:{})", user.getLogin(), user.getId(), plantDto.getLatinName(), plantDto.getId());
        plantService.update(plantDto, image, sketch);
        return ResponseEntity.ok(plantDto.getId());
    }

    @Override
    public ResponseEntity<Void> deletePlant(long id) {
        JwtUser user = UserUtil.getAuthUser();
        log.info("IN deletePlant - User {}(id:{}) is trying to delete plant id {}", user.getLogin(), user.getId(), id);
        plantService.delete(id);
        return ResponseEntity.ok().build();
    }
}
