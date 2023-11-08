package net.nazariiboiko.plantsearch.repository;

import net.nazariiboiko.plantsearch.entity.PlantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<PlantEntity, Long> {

    @Query(value = "SELECT * FROM plants ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<PlantEntity> findRandomPlant(@Param("limit") int limit);
    Page<PlantEntity> findByNameIsContainingIgnoreCaseOrLatinNameIsContainingIgnoreCase(String name, String latinName, Pageable pageable);
}
