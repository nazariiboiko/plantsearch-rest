package net.example.plantsearchrest.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import liquibase.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.dto.PlantPreviewDto;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.exception.NotFoundException;
import net.example.plantsearchrest.mapper.PlantMapper;
import net.example.plantsearchrest.model.PlantFilterModel;
import net.example.plantsearchrest.repository.PlantRepository;
import net.example.plantsearchrest.repository.S3Repository;
import net.example.plantsearchrest.service.PlantService;
import net.example.plantsearchrest.utils.PlantEntityCriteriaBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {
    private final Random random = new Random();
    @Value("${s3.pictures.bucket}")
    private String bucketName;
    @Value("${s3.pictures.type.image}")
    private String imagePath;
    @Value("${s3.pictures.type.sketch}")
    private String sketchePath;

    private final PlantRepository plantRep;
    private final S3Repository s3Rep;
    private final PlantMapper plantMapper = PlantMapper.INSTANCE;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PlantPreviewDto> getAll() {
        return plantRep.findAll().stream()
                .map(plantMapper::mapEntityToPreviewDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlantPreviewDto> getRandom(int amount) {
        if(amount < 1)
            amount = 1;
        if(amount > 100)
            amount = 100;

        List<PlantPreviewDto> randomList = new ArrayList<>();
        long max = getTotalRowCount();
        for(int i = 0; i < amount; i++) {
            PlantEntity entity = null;
            while(entity == null) {
                long randomIndex = random.nextLong() % 1300 + 1;
                entity = plantRep.findById(randomIndex).orElse(null);
            }
            randomList.add(plantMapper.mapEntityToPreviewDto(entity));
        }

        return randomList;
    }

    @Override
    public PlantDto getById(long id) throws NotFoundException {
        Optional<PlantEntity> entityOptional = plantRep.findById(id);
        if (entityOptional.isPresent()) {
            PlantEntity entity = entityOptional.get();
            return plantMapper.mapEntityToDto(entity);
        } else {
            throw new NotFoundException("Plant not found.", "NOT_FOUND");
        }
    }

    @Override
    public PlantEntity getEntityById(long id) {
        Optional<PlantEntity> entityOptional = plantRep.findById(id);
        if (entityOptional.isPresent()) {
            PlantEntity entity = entityOptional.get();
            return entity;
        } else {
            return null;
        }
    }

    @Override
    public PlantDto getByName(String name) {
        return plantMapper.mapEntityToDto(plantRep.getByName(name));
    }

    @Override
    public List<PlantPreviewDto> findByMatchingName(String name) {

        if (Character.UnicodeBlock.of(name.charAt(1)) == Character.UnicodeBlock.CYRILLIC) {
            return findByUaName(name);
        } else if (Character.UnicodeBlock.of(name.charAt(0)) == Character.UnicodeBlock.BASIC_LATIN) {
            return findByLaName(name);
        }
        return new ArrayList<>();
    }

    private List<PlantPreviewDto> findByUaName(String name) {
        return plantRep.findByNameIsContainingIgnoreCase(name).stream()
                .map(plantMapper::mapEntityToPreviewDto)
                .collect(Collectors.toList());
    }

    private List<PlantPreviewDto> findByLaName(String name) {
        return plantRep.findByLatinNameIsContainingIgnoreCase(name).stream()
                .map(plantMapper::mapEntityToPreviewDto)
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalRowCount() {
        return plantRep.count();
    }

    @Override
    public List<PlantPreviewDto> getAllByCriterias(PlantFilterModel filter) {
        return PlantEntityCriteriaBuilder.buildQuery(filter, entityManager).stream()
                .map(plantMapper::mapEntityToPreviewDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PlantEntity create(PlantDto plant, MultipartFile image, MultipartFile sketch) throws IOException {
        PlantEntity entity = new PlantEntity();
        entity.setId(-1L);
        plantMapper.updatePlantEntity(plant, entity);
        PlantEntity instance = plantRep.save(entity);

        if(image != null) {
            saveImageIntoS3(image, imagePath);
        }
        if(sketch != null) {
            saveImageIntoS3(sketch, sketchePath);
        }

        log.info("IN create - created id:{}", instance.getId());
        return instance;
    }

    @Override
    @Transactional
    public void update(PlantDto entity, MultipartFile image, MultipartFile sketch) throws IOException {
        if(entity.getId() == null) {
            create(entity, null, null);
        }
        PlantEntity plant = plantRep.findById(entity.getId()).orElseThrow(null);
        if(plant != null) {

            if(image != null && !StringUtil.equalsIgnoreCaseAndEmpty(image.getOriginalFilename(), plant.getImage())){
                deleteImageFromS3(plant.getImage(), imagePath);
                saveImageIntoS3(image, imagePath);
            }
            if(sketch != null && !StringUtil.equalsIgnoreCaseAndEmpty(sketch.getOriginalFilename(), plant.getSketch())) {
                deleteImageFromS3(plant.getSketch(), sketchePath);
                saveImageIntoS3(sketch, sketchePath);
            }

            log.info("IN update - previous values: {}, current values: {}", plant, entity);
            PlantMapper.INSTANCE.updatePlantEntity(entity, plant);
        } else {
            log.info("IN update - id {} not found", entity.getId());
        }
    }

    @Override
    public void delete(long id) {
        PlantEntity entity = plantRep.getById(id);
        try {
            if(StringUtil.isNotEmpty(entity.getImage())) {
                s3Rep.deleteImage(bucketName + imagePath, entity.getImage());
            }

            if(StringUtil.isNotEmpty(entity.getSketch())) {
                s3Rep.deleteImage(bucketName + sketchePath, entity.getSketch());
            }

            plantRep.deleteById(id);
            log.info("IN delete - plant id {} has been deleted successfully", id);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to delete the image", e);
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

    private void deleteImageFromS3(String imageName, String folderName) throws AmazonServiceException {
        try {
            s3Rep.deleteImage(bucketName + folderName, imageName);
            log.info("IN deleteImageFromS3 - image {} has been deleted from folder {} in bucket {}", imageName, folderName, bucketName);
        } catch (AmazonServiceException e) {
            throw new AmazonServiceException("Failed to delete the image", e);
        }
    }
}
