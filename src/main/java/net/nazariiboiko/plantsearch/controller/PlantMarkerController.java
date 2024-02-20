package net.nazariiboiko.plantsearch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nazariiboiko.plantsearch.api.PlantMarkerApi;
import net.nazariiboiko.plantsearch.dto.PlantMarkerGroupDto;
import net.nazariiboiko.plantsearch.service.PlantMarkerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlantMarkerController implements PlantMarkerApi {

    private final PlantMarkerService plantMarkerService;

    @Override
    public ResponseEntity<Page<PlantMarkerGroupDto>> getAllPlantMarkerGroups(int page, int size) {
        Pageable pageable = PageRequest.of(page, Math.min(size, 50));
        Page<PlantMarkerGroupDto> groups = plantMarkerService.getAllPlantMarkerGroups(pageable);
        log.info("IN getAllPlantMakerGroups - Returned page: {}|size: {} of plantMarkerGroups", page, size);
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PlantMarkerGroupDto> getPlantMarkerGroup(Long id) {
        PlantMarkerGroupDto groupDto = plantMarkerService.getPlantMarkerGroup(id);
        log.info("IN getPlantMarkerGroup - Returned marker's group with ID: {}", id);
        return new ResponseEntity<>(groupDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<PlantMarkerGroupDto>> getAllPlantMarkersForPlant(Long plantId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PlantMarkerGroupDto> plantMarkers = plantMarkerService.getAllPlantMarkerGroupsForPlant(pageable, plantId);
        log.info("IN getAllPlantMarkersForPlant - Returned page:{}|size:{} of plantMarkers", page, size);
        return new ResponseEntity<>(plantMarkers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Long> addPlantMarkerGroup(PlantMarkerGroupDto groupDto, MultipartFile image) {
        long createdId = plantMarkerService.savePlantMarkerGroup(groupDto, image);
        log.info("IN addPlantMarker - Create plantMarkerGroup with id: {} with {} markers", createdId, groupDto.getMarkers().size());
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<Void> deletePlantMarkerGroup(Long groupId) {
        plantMarkerService.deletePlantMarkerGroup(groupId);
        log.info("IN deletePlantMarker - plantMarkerGroup with id: {} has been deleted", groupId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Long> updatePlantMarkerGroup(PlantMarkerGroupDto groupDto, MultipartFile image) {
        plantMarkerService.updatePlantMarkerGroup(groupDto, image);
        log.info("IN updatePlantMarker - Marker Group with id: {} has been updated", groupDto.getId());
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
