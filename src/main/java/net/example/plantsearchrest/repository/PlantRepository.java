package net.example.plantsearchrest.repository;

import net.example.plantsearchrest.entity.PlantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<PlantEntity, Long> {
    PlantEntity getByName(String name);
    Page<PlantEntity> findAll(Pageable pageable);

    List<PlantEntity> findByNameIsContainingIgnoreCase(String name);
    List<PlantEntity> findByLatinNameIsContainingIgnoreCase(String name);
}
