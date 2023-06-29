package net.example.plantsearchrest.service;

import net.example.plantsearchrest.entity.PlantEntity;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PlantService {
    List<PlantEntity> getAll();
    Page<PlantEntity> getAll(Pageable pageable);

    List<PlantEntity> getRandom(int amount);
    PlantEntity getById(long id);
    PlantEntity getByName(String name);
    List<PlantEntity> findTop4ByName(String name);
    long getTotalRowCount();
    List<PlantEntity> executeQuery(String query);
    void update(PlantEntity entity);
}
