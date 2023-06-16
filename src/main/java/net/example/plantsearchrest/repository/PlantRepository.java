package net.example.plantsearchrest.repository;

import net.example.plantsearchrest.entity.PlantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<PlantEntity, Long> {
    PlantEntity getByName(String name);
    List<PlantEntity> findTop4ByNameIsContainingIgnoreCase(String name);
    List<PlantEntity> findTop4ByLatinNameIsContainingIgnoreCase(String name);
}
