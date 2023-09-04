package net.example.plantsearchrest.service;

import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.dto.PlantPreviewDto;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.exception.NotFoundException;
import net.example.plantsearchrest.model.PlantFilterModel;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

public interface PlantService {
    List<PlantPreviewDto> getAll();
    PlantDto getById(long id) throws NotFoundException;
    PlantEntity getEntityById(long id);
    List<PlantPreviewDto> getRandom(int amount);
    PlantDto getByName(String name);

    List<PlantPreviewDto> findByMatchingName(String name);

    long getTotalRowCount();

    List<PlantPreviewDto> getAllByCriterias(PlantFilterModel filter);

    @Transactional
    PlantEntity create(PlantDto plant, MultipartFile image, MultipartFile sketch) throws IOException;

    @Transactional
    void update(PlantDto entity, MultipartFile image, MultipartFile sketch) throws IOException;

    void delete(long id);
}
