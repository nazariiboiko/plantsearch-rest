package net.nazariiboiko.plantsearch.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nazariiboiko.plantsearch.dto.PlantDto;
import net.nazariiboiko.plantsearch.dto.PlantPreviewDto;
import net.nazariiboiko.plantsearch.entity.PlantEntity;
import net.nazariiboiko.plantsearch.exception.PageNotFoundException;
import net.nazariiboiko.plantsearch.exception.PlantAlreadyExistsException;
import net.nazariiboiko.plantsearch.exception.PlantNotFoundException;
import net.nazariiboiko.plantsearch.mapper.PlantMapper;
import net.nazariiboiko.plantsearch.model.PlantFilterModel;
import net.nazariiboiko.plantsearch.repository.PlantRepository;
import net.nazariiboiko.plantsearch.repository.S3Repository;
import net.nazariiboiko.plantsearch.service.PlantService;
import net.nazariiboiko.plantsearch.util.PlantFilterBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {

    @Value("${s3.pictures.bucket}")
    private String bucketName;
    @Value("${s3.pictures.type.image}")
    private String imagePath;
    @Value("${s3.pictures.type.sketch}")
    private String sketchePath;

    private final S3Repository s3Rep;
    private final PlantMapper plantMapper = PlantMapper.INSTANCE;
    private final EntityManager entityManager;
    private final PlantRepository plantRep;

    @Override
    public Page<PlantPreviewDto> getAllPlants(Pageable pageable) {
        Page<PlantEntity> page = plantRep.findAll(pageable);
        return page.map(plantMapper::mapEntityToPreviewDto);
    }

    @Override
    public PlantDto getPlant(Long id) throws PlantNotFoundException {
        PlantEntity entity = plantRep.findById(id)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found", "service.plant.not_found"));
        return plantMapper.mapEntityToDto(entity);
    }

    @Override
    public List<PlantPreviewDto> getRandomPlants(int amount) {
        return plantRep.findRandomPlant(amount).stream()
                .map(plantMapper::mapEntityToPreviewDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PlantPreviewDto> getPlantsByName(String name, Pageable pageable) {
        Page<PlantEntity> page = plantRep.findByNameIsContainingIgnoreCaseOrLatinNameIsContainingIgnoreCase(name, name, pageable);
        return page.map(plantMapper::mapEntityToPreviewDto);
    }

    @Override
    public List<PlantPreviewDto> getPlantsByName(String name, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return plantRep.findByNameIsContainingIgnoreCaseOrLatinNameIsContainingIgnoreCase(name, name, pageable).stream()
                .map(plantMapper::mapEntityToPreviewDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PlantPreviewDto> getPlantsByFilter(PlantFilterModel filter, Pageable pageable) {
        List<PlantEntity> entities = PlantFilterBuilder.buildQuery(filter, entityManager);
        int startIndex = pageable.getPageNumber() * pageable.getPageSize();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), entities.size());
        long total = entities.size();
        if(startIndex < endIndex) {
            List<PlantPreviewDto> dtoList = entities.subList(startIndex, endIndex).stream()
                    .map(plantMapper::mapEntityToPreviewDto)
                    .collect(Collectors.toList());

            return new PageImpl<>(dtoList, pageable, total - pageable.getPageSize());
        }
        return null;
    }

    @Override
    public long createPlant(PlantDto plantDto, MultipartFile image, MultipartFile sketch) {
        if (plantDto.getId() != null && plantRep.existsById(plantDto.getId())) {
            throw new PlantAlreadyExistsException("Plant already exists", "service.plant.already_exists");
        }
        PlantEntity entity = plantMapper.mapDtoToEntity(plantDto);
        entity.setId(-1L);
        entity = plantRep.save(entity);

        if(image != null) {
            saveImageIntoS3(image, imagePath);
        }
        if(sketch != null) {
            saveImageIntoS3(sketch, sketchePath);
        }

        return entity.getId();
    }

    @Override
    @Transactional
    public void update(PlantDto plantDto, MultipartFile image, MultipartFile sketch) {
        PlantEntity plantToUpdate = plantRep
                .findById(plantDto.getId())
                .orElseThrow(() -> new PlantNotFoundException("Plant not found", "service.plant.not_found"));
        plantMapper.updatePlantEntity(plantDto, plantToUpdate);
        plantRep.save(plantToUpdate);

        if(image != null) {
            saveImageIntoS3(image, imagePath);
        }
        if(sketch != null) {
            saveImageIntoS3(sketch, sketchePath);
        }
    }

    private void saveImageIntoS3(MultipartFile image, String folderName) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(image.getSize());
            metadata.setContentType(image.getContentType());
            s3Rep.uploadImage(bucketName + folderName, image.getOriginalFilename(), image.getInputStream(), metadata);
            log.info("IN saveImageIntoS3 - image {} was successfully saved into folder {} in bucket {}", image.getOriginalFilename(), folderName, bucketName);
        } catch (IOException exp) {
            throw new IllegalStateException("Failed to read the image", exp);
        }
    }
}
