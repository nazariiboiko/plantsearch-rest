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

    /**
     * Builds a dynamic query to filter plant entities based on the provided filter criteria.
     *
     * @param filter        The filter criteria used to construct the query.
     * @param entityManager The EntityManager to execute the query.
     * @return A list of PlantEntity objects that match the filter criteria.
     */
    public static List<PlantEntity> buildQuery(PlantFilterModel filter, EntityManager entityManager) {
        // Initialize criteria builder and query
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
            List<Predicate> orPredicates = new ArrayList<>();
            for (String habitus : filterDataModel.getHabitus()) {
                String f1 = "%" + habitus.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("habitus")),
                        f1
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filterDataModel.getGrowthRate() != null && !filterDataModel.getGrowthRate().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String rate : filterDataModel.getGrowthRate()) {
                String f1 = "%" + rate.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("growthRate")),
                        f1
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filterDataModel.getColor() != null && !filterDataModel.getColor().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String color : filterDataModel.getColor()) {
                String f1 = "%" + color.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("color")),
                        f1
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filterDataModel.getRecommendation() != null && !filterDataModel.getRecommendation().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String recommendation : filterDataModel.getRecommendation()) {
                String f1 = "%" + recommendation.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("recommendation")),
                        f1
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filterDataModel.getFrostResistance() != null && !filterDataModel.getFrostResistance().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String resistance : filterDataModel.getFrostResistance()) {
                String floweringPeriod = "%" + resistance.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("frostResistance")),
                        floweringPeriod
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filterDataModel.getLighting() != null && !filterDataModel.getLighting().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String light : filterDataModel.getLighting()) {
                String floweringPeriod = "%" + light.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("lighting")),
                        floweringPeriod
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filterDataModel.getEvergreen() != null && !filterDataModel.getEvergreen().isEmpty()) {
            predicates.add(root.get("evergreen").in(filterDataModel.getEvergreen()));
        }

        if (filterDataModel.getFloweringPeriod() != null && !filterDataModel.getFloweringPeriod().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String period : filterDataModel.getFloweringPeriod()) {
                String floweringPeriod = "%" + period.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("floweringPeriod")),
                        floweringPeriod
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filterDataModel.getPlantType() != null && !filterDataModel.getPlantType().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String type : filterDataModel.getPlantType()) {
                String f1 = "%" + type.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("plantType")),
                        f1
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }
        
        if (filterDataModel.getZoning() != null && !filterDataModel.getZoning().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String moisture : filterDataModel.getZoning()) {
                String zoning = "%" + moisture.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("zoning")),
                        zoning
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filterDataModel.getPh() != null && !filterDataModel.getPh().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String moisture : filterDataModel.getPh()) {
                String ph = "%" + moisture.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("ph")),
                        ph
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filterDataModel.getSoilMoisture() != null && !filterDataModel.getSoilMoisture().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String moisture : filterDataModel.getSoilMoisture()) {
                String floweringPeriod = "%" + moisture.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("soilMoisture")),
                        floweringPeriod
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filterDataModel.getHardy() != null && !filterDataModel.getHardy().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String nutrition : filterDataModel.getNutrition()) {
                String hardy = "%" + nutrition.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("hardy")),
                        hardy
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filterDataModel.getNutrition() != null && !filterDataModel.getNutrition().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String nutrition : filterDataModel.getNutrition()) {
                String floweringPeriod = "%" + nutrition.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nutrition")),
                        floweringPeriod
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
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
                    .map(color -> criteriaBuilder.like(root.get("floweringColor"), "%" + color + "%"))
                    .collect(Collectors.toList());

            predicates.add(criteriaBuilder.or(floweringColor.toArray(new Predicate[0])));
        }

        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
