package net.example.plantsearchrest.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import liquibase.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.mapper.PlantMapper;
import net.example.plantsearchrest.model.FolderName;
import net.example.plantsearchrest.model.PlantFilterModel;
import net.example.plantsearchrest.repository.PlantRepository;
import net.example.plantsearchrest.repository.S3Repository;
import net.example.plantsearchrest.service.PlantService;
import net.example.plantsearchrest.utils.PlantEntityCriteriaBuilder;
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
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {
    private final Random random = new Random();
    private final String bucketName = "plantsearch/";

    private final PlantRepository plantRep;
    private final S3Repository s3Rep;
    private final PlantMapper plantMapper = PlantMapper.INSTANCE;
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<PlantDto> getAll() {
        return plantRep.findAll().stream()
                .map(plantMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlantDto> getRandom(int amount) {
        if(amount < 1)
            amount = 1;
        if(amount > 50)
            amount = 50;

        List<PlantEntity> randomList = new ArrayList<>();
        IntStream.range(0, amount).forEach(x -> randomList.add(plantRep.getById((long) (Math.abs(random.nextInt() % 500) + 1))));

        return randomList.stream()
                .map(plantMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlantDto getById(long id) {
        Optional<PlantEntity> entityOptional = plantRep.findById(id);
        if (entityOptional.isPresent()) {
            PlantEntity entity = entityOptional.get();
            return plantMapper.mapEntityToDto(entity);
        } else {
            return null;
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
    public List<PlantDto> findByMatchingName(String name) {

        if (Character.UnicodeBlock.of(name.charAt(1)) == Character.UnicodeBlock.CYRILLIC) {
            return findByUaName(name);
        } else if (Character.UnicodeBlock.of(name.charAt(0)) == Character.UnicodeBlock.BASIC_LATIN) {
            return findByLaName(name);
        }
        return new ArrayList<>();
    }

    private List<PlantDto> findByUaName(String name) {
        return plantRep.findByNameIsContainingIgnoreCase(name).stream()
                .map(plantMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private List<PlantDto> findByLaName(String name) {
        return plantRep.findByLatinNameIsContainingIgnoreCase(name).stream()
                .map(plantMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalRowCount() {
        return plantRep.count();
    }

    @Override
    public List<PlantDto> getAllByCriterias(PlantFilterModel filter) {
        return PlantEntityCriteriaBuilder.buildQuery(filter, entityManager).stream()
                .map(plantMapper::mapEntityToDto)
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
            saveImageIntoS3(image, FolderName.IMAGE);
        }
        if(sketch != null) {
            saveImageIntoS3(sketch, FolderName.SKETCH);
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
                deleteImageFromS3(plant.getImage(), FolderName.IMAGE);
                saveImageIntoS3(image, FolderName.IMAGE);
            }
            if(sketch != null && !StringUtil.equalsIgnoreCaseAndEmpty(sketch.getOriginalFilename(), plant.getSketch())) {
                deleteImageFromS3(plant.getSketch(), FolderName.SKETCH);
                saveImageIntoS3(sketch, FolderName.SKETCH);
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
                s3Rep.deleteImage(bucketName + FolderName.IMAGE.getValue(), entity.getImage());
            }

            if(StringUtil.isNotEmpty(entity.getSketch())) {
                s3Rep.deleteImage(bucketName + FolderName.SKETCH.getValue(), entity.getSketch());
            }

            plantRep.deleteById(id);
            log.info("IN delete - plant id {} has been deleted successfully", id);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to delete the image", e);
        }
    }

    private void saveImageIntoS3(MultipartFile image, FolderName folderName) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(image.getSize());
            metadata.setContentType(image.getContentType());
            s3Rep.uploadImage(bucketName + folderName.getValue(), image.getOriginalFilename(), image.getInputStream(), metadata);
            log.info("IN saveImageIntoS3 - image {} was successfully saved into folder {} in bucket {}", image.getOriginalFilename(), folderName, bucketName);
        } catch (IOException exp) {
            throw new IllegalStateException("Failed to read the image", exp);
        }
    }

    private void deleteImageFromS3(String imageName, FolderName folderName) throws AmazonServiceException {
        try {
            s3Rep.deleteImage(bucketName + folderName.getValue(), imageName);
            log.info("IN deleteImageFromS3 - image {} has been deleted from folder {} in bucket {}", imageName, folderName, bucketName);
        } catch (AmazonServiceException e) {
            throw new AmazonServiceException("Failed to delete the image", e);
        }
    }
}
