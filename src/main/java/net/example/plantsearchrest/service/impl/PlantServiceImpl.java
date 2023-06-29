package net.example.plantsearchrest.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.mapper.PlantMapper;
import net.example.plantsearchrest.repository.PlantRepository;
import net.example.plantsearchrest.service.PlantService;
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
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PlantEntity> getAll() {
        log.info("IN getAll | return all objects");
        return plantRep.findAll();
    }

    @Override
    public Page<PlantEntity> getAll(Pageable pageable) {
        return plantRep.findAll(pageable);
    }

    @Override
    public List<PlantEntity> getRandom(int amount) {
        log.info("IN getRandom | return {} objects", amount);
        List<PlantEntity> randomList = new ArrayList<>();
        IntStream.range(0, amount).forEach(x -> randomList.add(plantRep.getReferenceById((long) (Math.abs(random.nextInt() % 500) + 1))));
        return randomList;
    }

    @Override
    public PlantEntity getById(long id) {
        log.info("IN getById | return object with {} id", id);
        return plantRep.getReferenceById(id);
    }

    @Override
    public PlantEntity getByName(String name) {
        log.info("IN getByName | return {} object", name);
        return plantRep.getByName(name);
    }

    @Override
    public List<PlantEntity> findTop4ByName(String name) {

        if (Character.UnicodeBlock.of(name.charAt(1)) == Character.UnicodeBlock.CYRILLIC) {
            log.info("IN findTop4MatchingByName| return objects by cyryllic name {}", name);
            return findTop4ByUaName(name);
        } else if (Character.UnicodeBlock.of(name.charAt(0)) == Character.UnicodeBlock.BASIC_LATIN) {
            log.info("IN findTop4MatchingByName| return objects by latin name {}", name);
            return findTop4ByLaName(name);
        } else {
            log.error("IN findTop4MatchingByName| not recognized symbol {}", name);
        }
        return new ArrayList<PlantEntity>();
    }

    private List<PlantEntity> findTop4ByUaName(String name) {
        return plantRep.findTop4ByNameIsContainingIgnoreCase(name);
    }

    private List<PlantEntity> findTop4ByLaName(String name) {
        return plantRep.findTop4ByLatinNameIsContainingIgnoreCase(name);
    }

    @Override
    public long getTotalRowCount() {
        log.info("IN getTotalRowCount | return current count");
        return plantRep.count();
    }

    @Override
    public List<PlantEntity> executeQuery(String query) {
        log.info("IN executeQuery - executed {}", query);
        return entityManager.createQuery(query, PlantEntity.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void update(PlantEntity entity) {
        log.info("IN update - updated id:{}", entity.getId());
        PlantEntity plant = plantRep.findById(entity.getId()).orElseThrow();
        PlantMapper.INSTANCE.updatePlantEntity(entity, plant);
    }
}
