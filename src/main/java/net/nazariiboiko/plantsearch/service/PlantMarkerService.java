package net.nazariiboiko.plantsearch.service;

import net.nazariiboiko.plantsearch.dto.PlantMarkerGroupDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PlantMarkerService {
    
    Page<PlantMarkerGroupDto> getAllPlantMarkerGroupsForPlant(Pageable pageable, Long plantId);

    Page<PlantMarkerGroupDto> getAllPlantMarkerGroups(Pageable pageable);

    long savePlantMarkerGroup(PlantMarkerGroupDto groupDto, MultipartFile image);

    PlantMarkerGroupDto getPlantMarkerGroup(Long groupId);

    void deletePlantMarkerGroup(Long id);

    void updatePlantMarkerGroup(PlantMarkerGroupDto groupDto, MultipartFile image);
}
