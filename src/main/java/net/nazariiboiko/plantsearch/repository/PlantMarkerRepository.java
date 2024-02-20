package net.nazariiboiko.plantsearch.repository;

import net.nazariiboiko.plantsearch.entity.PlantMarkerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantMarkerRepository extends JpaRepository<PlantMarkerEntity, Long> {

    Page<PlantMarkerEntity> findAllByPlantId(Pageable pageable, Long plantId);
    void deleteAllByGroupId(Long groupId);
}
