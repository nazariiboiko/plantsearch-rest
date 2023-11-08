package net.nazariiboiko.plantsearch.service;

import net.nazariiboiko.plantsearch.dto.PlantDto;
import net.nazariiboiko.plantsearch.dto.PlantPreviewDto;
import net.nazariiboiko.plantsearch.exception.PlantNotFoundException;
import net.nazariiboiko.plantsearch.model.PlantFilterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlantService {
    Page<PlantPreviewDto> getAllPlants(Pageable pageable);

    PlantDto getPlant(Long id) throws PlantNotFoundException;

    List<PlantPreviewDto> getRandomPlants(int amount);

    Page<PlantPreviewDto> getPlantsByName(String name, Pageable pageable);

    List<PlantPreviewDto> getPlantsByName(String name, int limit);

    Page<PlantPreviewDto> getPlantsByFilter(PlantFilterModel filter, Pageable pageable);

    long createPlant(PlantDto plantDto, MultipartFile image, MultipartFile sketch);

    void update(PlantDto plantDto, MultipartFile image, MultipartFile sketch);
}
