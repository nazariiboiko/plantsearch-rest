package net.nazariiboiko.plantsearch.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nazariiboiko.plantsearch.dto.PlantMarkerDto;
import net.nazariiboiko.plantsearch.dto.PlantMarkerGroupDto;
import net.nazariiboiko.plantsearch.entity.PlantMarkerEntity;
import net.nazariiboiko.plantsearch.entity.PlantMarkerGroupEntity;
import net.nazariiboiko.plantsearch.exception.AlreadyExistsException;
import net.nazariiboiko.plantsearch.exception.PlantNotFoundException;
import net.nazariiboiko.plantsearch.exception.ServiceExceptionExtended;
import net.nazariiboiko.plantsearch.mapper.PlantMarkerMapper;
import net.nazariiboiko.plantsearch.repository.PlantMarkerGroupRepository;
import net.nazariiboiko.plantsearch.repository.PlantMarkerRepository;
import net.nazariiboiko.plantsearch.service.AwsS3Service;
import net.nazariiboiko.plantsearch.service.PlantMarkerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantMarkerServiceImpl implements PlantMarkerService {

    @Value("${s3.pictures.type.markers}")
    private String folderToSave;

    private final PlantMarkerRepository markerRep;
    private final PlantMarkerGroupRepository groupRep;
    private final PlantMarkerMapper plantMarkerMapper;
    private final AwsS3Service s3Service;

    @Override
    public Page<PlantMarkerGroupDto> getAllPlantMarkerGroups(Pageable pageable) {
        return groupRep.findAll(pageable).map(plantMarkerMapper::mapGroupEntityToDtoIgnoreMarkers);
    }

    @Override
    public PlantMarkerGroupDto getPlantMarkerGroup(Long groupId) {
        PlantMarkerGroupEntity groupEntity = groupRep.findById(groupId)
                .orElseThrow(() -> new PlantNotFoundException("Group not found", "service.plant.not_found"));

        return plantMarkerMapper.mapGroupEntityToDto(groupEntity);
    }

    @Override
    public Page<PlantMarkerGroupDto> getAllPlantMarkerGroupsForPlant(Pageable pageable, Long plantId) {
        return groupRep.findByMarkers_PlantId(plantId, pageable).map(plantMarkerMapper::mapGroupEntityToDtoIgnoreMarkers);
    }

    @Override
    @Transactional
    public long savePlantMarkerGroup(PlantMarkerGroupDto groupDto, MultipartFile image) {
        if (image == null || image.isEmpty())
            throw new ServiceExceptionExtended("Image is null", "service.markers.image_required");

        if(groupDto.getId() != null && groupRep.existsById(groupDto.getId()))
            throw new AlreadyExistsException("Plant Marker Group already exists", "service.markers.already_exists");

        String fileName = "image_" + LocalTime.now().toString().replace(".", "_").replace(":","_") + ".jpg";
        groupDto.setSrc(fileName);
        if(!s3Service.saveFile(image, fileName, folderToSave))
            throw new ServiceExceptionExtended("Failed to save file to S3.", "service.markers.failed");

        PlantMarkerGroupEntity createdGroupEntity = groupRep.save(plantMarkerMapper.mapGroupDtoToEntityIgnoreMarkers(groupDto));
        if (groupDto.getMarkers() != null && !groupDto.getMarkers().isEmpty()) {
            for (PlantMarkerDto marker : groupDto.getMarkers()) {
                PlantMarkerEntity markerEntity = plantMarkerMapper.mapDtoToEntity(marker);
                markerEntity.setGroup(createdGroupEntity);
                markerEntity.setId(-1L);
                markerRep.save(markerEntity);
            }
        }
        return createdGroupEntity.getId();
    }

    @Override
    public void deletePlantMarkerGroup(Long id) {
        if(id == null || !groupRep.existsById(id))
            throw new ServiceExceptionExtended("Marker group not found", "service.markers.not_found");

        groupRep.deleteById(id);
    }

    @Override
    @Transactional
    public void updatePlantMarkerGroup(PlantMarkerGroupDto groupDto, MultipartFile image) {
        Long groupId = groupDto.getId();
        if (groupId == null || !groupRep.existsById(groupId)) {
            throw new ServiceExceptionExtended("Marker group not found", "service.markers.not_found");
        }

        PlantMarkerGroupEntity existingGroupEntity = groupRep.findById(groupId).orElseThrow(() -> new ServiceExceptionExtended("Not found", "service.markers.not_found"));
        if (groupDto.getMarkers() != null && !groupDto.getMarkers().isEmpty()) {
            markerRep.deleteAllByGroupId(groupId);
            for (PlantMarkerDto marker : groupDto.getMarkers()) {
                PlantMarkerEntity markerEntity = plantMarkerMapper.mapDtoToEntity(marker);
                markerEntity.setGroup(existingGroupEntity);
                markerEntity.setId(-1L);
                markerRep.save(markerEntity);
            }
        }

        if (image != null && !image.isEmpty()) {
            String fileName = "image_" + LocalTime.now().toString().replace(".", "_").replace(":", "_") + ".jpg";
            existingGroupEntity.setSrc(fileName);

            if (!s3Service.saveFile(image, fileName, folderToSave)) {
                throw new ServiceExceptionExtended("Failed to save file to S3.", "service.markers.failed");
            }
        }

        groupRep.save(existingGroupEntity);
    }
}
