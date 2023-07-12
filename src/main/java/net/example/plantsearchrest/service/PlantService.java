package net.example.plantsearchrest.service;

import javax.transaction.Transactional;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.model.PlantFilterModel;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
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
    PlantEntity create(PlantDto entity);

    @Transactional
    void update(PlantDto entity);

    void delete(long id);
}
