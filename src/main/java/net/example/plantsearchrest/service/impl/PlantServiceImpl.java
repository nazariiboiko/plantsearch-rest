package net.example.plantsearchrest.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.mapper.PlantMapper;
import net.example.plantsearchrest.model.PlantFilterModel;
import net.example.plantsearchrest.repository.PlantRepository;
import net.example.plantsearchrest.service.PlantService;
import net.example.plantsearchrest.utils.PlantEntityCriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {

    private final PlantRepository plantRep;
    private final Random random = new Random();
    private final PlantMapper plantMapper = PlantMapper.INSTANCE;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PlantEntity> getAll() {
        log.info("IN getAll | return all objects");
        return plantRep.findAll();
    }

    @Override
    public List<PlantEntity> getRandom(int amount) {
        log.info("IN getRandom | return {} objects", amount);
        List<PlantEntity> randomList = new ArrayList<>();
        IntStream.range(0, amount).forEach(x -> randomList.add(plantRep.getById((long) (Math.abs(random.nextInt() % 500) + 1))));
        return randomList;
    }

    @Override
    public PlantEntity getById(long id) {
        log.info("IN getById | return object with {} id", id);
        return plantRep.getById(id);
    }

    @Override
    public PlantEntity getByName(String name) {
        log.info("IN getByName | return {} object", name);
        return plantRep.getByName(name);
    }

    @Override
    public List<PlantEntity> findByMatchingName(String name) {

        if (Character.UnicodeBlock.of(name.charAt(1)) == Character.UnicodeBlock.CYRILLIC) {
            log.info("IN findByMatchingName| return objects by cyryllic name {}", name);
            return findByUaName(name);
        } else if (Character.UnicodeBlock.of(name.charAt(0)) == Character.UnicodeBlock.BASIC_LATIN) {
            log.info("IN finByMatchingName| return objects by latin name {}", name);
            return findByLaName(name);
        } else {
            log.error("IN findByMatchingName| not recognized symbol {}", name);
        }
        return new ArrayList<PlantEntity>();
    }

    private List<PlantEntity> findByUaName(String name) {
        return plantRep.findByNameIsContainingIgnoreCase(name);
    }

    private List<PlantEntity> findByLaName(String name) {
        return plantRep.findByLatinNameIsContainingIgnoreCase(name);
    }

    @Override
    public long getTotalRowCount() {
        log.info("IN getTotalRowCount | return current count");
        return plantRep.count();
    }

    @Override
    public List<PlantEntity> getAllByCriterias(PlantFilterModel filter) {
        return PlantEntityCriteriaBuilder.buildQuery(filter, entityManager);
    }

    @Override
    @Transactional
    public PlantEntity create(PlantDto plant) {
        PlantEntity entity = new PlantEntity();
        entity.setId(-1L);
        plantMapper.updatePlantEntity(plant, entity);
        PlantEntity instance = plantRep.save(entity);
        log.info("IN create - created id:{}", instance.getId());
        return instance;
    }

    @Override
    @Transactional
    public void update(PlantDto entity) {
        if(entity.getId() == null) {
            create(entity);
        }
        log.info("IN update - starting update plant id:{}", entity.getId());
        PlantEntity plant = plantRep.findById(entity.getId()).orElseThrow(null);
        if(plant != null) {
            log.info("IN update - previous values: {}", plant);
            log.info("IN update - current values: {}", entity);
            PlantMapper.INSTANCE.updatePlantEntity(entity, plant);
        }
    }

    @Override
    public void delete(long id) {
        plantRep.deleteById(id);
        log.info("IN delete - plant id {} has been deleted successfully", id);
    }
}
