package net.example.plantsearchrest.utils;

import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.model.PlantFilterDataModel;
import net.example.plantsearchrest.model.PlantFilterModel;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PlantEntityCriteriaBuilder {

    public static List<PlantEntity> buildQuery(PlantFilterModel filter, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PlantEntity> criteriaQuery = criteriaBuilder.createQuery(PlantEntity.class);
        Root<PlantEntity> root = criteriaQuery.from(PlantEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        PlantFilterDataModel filterDataModel = filter.getData();

        if (filterDataModel.getName() != null && filterDataModel.getName().length() > 0) {
            String name = filterDataModel.getName();
            Predicate namePredicate;
            if (Character.UnicodeBlock.of(name.charAt(0)) == Character.UnicodeBlock.CYRILLIC) {
                namePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
            } else if (Character.UnicodeBlock.of(name.charAt(0)) == Character.UnicodeBlock.BASIC_LATIN) {
                namePredicate = criteriaBuilder.like(root.get("latinName"), "%" + name + "%");
            } else {
                namePredicate = criteriaBuilder.disjunction();
            }
            predicates.add(namePredicate);
        }

        if (filterDataModel.getHabitus() != null && !filterDataModel.getHabitus().isEmpty()) {
            predicates.add(root.get("habitus").in(filterDataModel.getHabitus()));
        }

        if (filterDataModel.getGrowthRate() != null && !filterDataModel.getGrowthRate().isEmpty()) {
            predicates.add(root.get("growthRate").in(filterDataModel.getGrowthRate()));
        }

        if (filterDataModel.getColor() != null && !filterDataModel.getColor().isEmpty()) {
            predicates.add(root.get("color").in(filterDataModel.getColor()));
        }

        if (filterDataModel.getFrostResistance() != null && !filterDataModel.getFrostResistance().isEmpty()) {
            predicates.add(root.get("frostResistance").in(filterDataModel.getFrostResistance()));
        }

        if (filterDataModel.getRecommendation() != null && !filterDataModel.getRecommendation().isEmpty()) {
            predicates.add(root.get("recommendation").in(filterDataModel.getRecommendation()));
        }

        if (filterDataModel.getLighting() != null && !filterDataModel.getLighting().isEmpty()) {
            predicates.add(root.get("lighting").in(filterDataModel.getLighting()));
        }

        if (filterDataModel.getEvergreen() != null && !filterDataModel.getEvergreen().isEmpty()) {
            predicates.add(root.get("evergreen").in(filterDataModel.getEvergreen()));
        }

        if (filterDataModel.getFloweringPeriod() != null && !filterDataModel.getFloweringPeriod().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("floweringPeriod"), "%" + filterDataModel.getFloweringPeriod() + "%"));
        }

        if (filterDataModel.getPlantType() != null && !filterDataModel.getPlantType().isEmpty()) {
            predicates.add(root.get("plantType").in(filterDataModel.getPlantType()));
        }

        if (filterDataModel.getZoning() != null && !filterDataModel.getZoning().isEmpty()) {
            predicates.add(root.get("zoning").in(filterDataModel.getZoning()));
        }

        if (filterDataModel.getPh() != null && !filterDataModel.getPh().isEmpty()) {
            predicates.add(root.get("ph").in(filterDataModel.getPh()));
        }

        if (filterDataModel.getSoilMoisture() != null && !filterDataModel.getSoilMoisture().isEmpty()) {
            predicates.add(root.get("soilMoisture").in(filterDataModel.getSoilMoisture()));
        }

        if (filterDataModel.getHardy() != null && !filterDataModel.getHardy().isEmpty()) {
            predicates.add(root.get("hardy").in(filterDataModel.getHardy()));
        }

        if (filterDataModel.getNutrition() != null && !filterDataModel.getNutrition().isEmpty()) {
            predicates.add(root.get("nutrition").in(filterDataModel.getNutrition()));
        }

        if (filterDataModel.getSummerColor() != null && !filterDataModel.getSummerColor().isEmpty()) {
            List<Predicate> colorPredicates = filterDataModel.getSummerColor()
                    .stream()
                    .map(color -> color.substring(4))
                    .map(color -> criteriaBuilder.like(root.get("summerColor"), "%" + color + "%"))
                    .collect(Collectors.toList());

            predicates.add(criteriaBuilder.or(colorPredicates.toArray(new Predicate[0])));
        }

        if (filterDataModel.getAutumnColor() != null && !filterDataModel.getAutumnColor().isEmpty()) {
            List<Predicate> autumnColorPredicates = filterDataModel.getAutumnColor()
                    .stream()
                    .map(color -> color.substring(4))
                    .map(color -> criteriaBuilder.like(root.get("autumnColor"), "%" + color + "%"))
                    .collect(Collectors.toList());

            predicates.add(criteriaBuilder.or(autumnColorPredicates.toArray(new Predicate[0])));
        }

        if (filterDataModel.getFloweringColor() != null && !filterDataModel.getFloweringColor().isEmpty()) {
            List<Predicate> floweringColor = filterDataModel.getFloweringColor()
                    .stream()
                    .map(color -> color.substring(4))
                    .map(color -> criteriaBuilder.like(root.get("autumnColor"), "%" + color + "%"))
                    .collect(Collectors.toList());

            predicates.add(criteriaBuilder.or(floweringColor.toArray(new Predicate[0])));
        }

        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }

        log.info("IN buildQuery - executing query {} ", criteriaQuery.toString());
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
