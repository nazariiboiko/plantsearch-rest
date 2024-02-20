package net.nazariiboiko.plantsearch.repository;

import net.nazariiboiko.plantsearch.entity.PlantMarkerGroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantMarkerGroupRepository extends JpaRepository<PlantMarkerGroupEntity, Long> {
    Page<PlantMarkerGroupEntity> findByMarkers_PlantId(Long plantId, Pageable pageable);

}
