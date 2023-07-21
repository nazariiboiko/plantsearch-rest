package net.example.plantsearchrest.service;

import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.model.PlantFilterModel;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

public interface PlantService {
    List<PlantEntity> getAll();


    List<PlantEntity> getRandom(int amount);
    PlantEntity getById(long id);
    PlantEntity getByName(String name);

    List<PlantEntity> findByMatchingName(String name);

    long getTotalRowCount();

    List<PlantEntity> getAllByCriterias(PlantFilterModel filter);

    @Transactional
    PlantEntity create(PlantDto plant, MultipartFile image, MultipartFile sketch) throws IOException;

    @Transactional
    void update(PlantDto entity, MultipartFile image, MultipartFile sketch) throws IOException;

    void delete(long id);
}
