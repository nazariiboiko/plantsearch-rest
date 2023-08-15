package net.example.plantsearchrest.service;

import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.model.PlantFilterModel;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

public interface PlantService {
    List<PlantDto> getAll();
    PlantDto getById(long id);
    PlantEntity getEntityById(long id);
    List<PlantDto> getRandom(int amount);
    PlantDto getByName(String name);

    List<PlantDto> findByMatchingName(String name);

    long getTotalRowCount();

    List<PlantDto> getAllByCriterias(PlantFilterModel filter);

    @Transactional
    PlantEntity create(PlantDto plant, MultipartFile image, MultipartFile sketch) throws IOException;

    @Transactional
    void update(PlantDto entity, MultipartFile image, MultipartFile sketch) throws IOException;

    void delete(long id);
}
