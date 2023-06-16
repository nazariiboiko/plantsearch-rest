package net.example.plantsearchrest.service;

import net.example.plantsearchrest.PlantSearchRestApplication;
import net.example.plantsearchrest.entity.PlantEntity;

import java.util.HashMap;
import java.util.List;

public interface PlantService {
    List<PlantEntity> getAll();
    List<PlantEntity> getRandom(int amount);
    PlantEntity getById(long id);
    PlantEntity getByName(String name);
    List<PlantEntity> findTop4ByName(String name);
    List<PlantEntity> filterPlants(HashMap<String, String> plantCriterias);
    long getTotalRowCount();
}
