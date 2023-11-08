package net.nazariiboiko.plantsearch.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import net.nazariiboiko.plantsearch.entity.PlantEntity;
import net.nazariiboiko.plantsearch.model.PlantFilterModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlantFilterBuilder {

    /**
     * Builds a dynamic query to filter plant entities based on the provided filter criteria.
     * @param filter        The filter criteria used to construct the query.
     * @param entityManager The EntityManager to execute the query.
     * @return A list of PlantEntity objects that match the filter criteria.
     */
    public static List<PlantEntity> buildQuery(PlantFilterModel filter, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PlantEntity> criteriaQuery = criteriaBuilder.createQuery(PlantEntity.class);
        Root<PlantEntity> root = criteriaQuery.from(PlantEntity.class);

        Predicate finalPredicate = buildPredicate(filter, criteriaBuilder, root);
        criteriaQuery.where(finalPredicate);

        List<PlantEntity> resultList = entityManager
                .createQuery(criteriaQuery)
                .getResultList();

        return resultList;
    }

    private static Predicate buildPredicate(PlantFilterModel filter, CriteriaBuilder criteriaBuilder, Root root) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getName() != null && filter.getName().length() > 0) {
            String name = filter.getName();
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


        if (filter.getHabitus() != null && !filter.getHabitus().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String habitus : filter.getHabitus()) {
                String f1 = "%" + habitus.toLowerCase() + "%";
                Expression<String> habitusExpression = criteriaBuilder.lower(root.get("habitus"));
                orPredicates.add(criteriaBuilder.like(habitusExpression, f1));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filter.getGrowthRate() != null && !filter.getGrowthRate().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String rate : filter.getGrowthRate()) {
                String f1 = "%" + rate.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("growthRate")),
                        f1
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filter.getColor() != null && !filter.getColor().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String color : filter.getColor()) {
                String f1 = "%" + color.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("color")),
                        f1
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filter.getRecommendation() != null && !filter.getRecommendation().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String recommendation : filter.getRecommendation()) {
                String f1 = "%" + recommendation.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("recommendation")),
                        f1
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filter.getFrostResistance() != null && !filter.getFrostResistance().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String resistance : filter.getFrostResistance()) {
                String floweringPeriod = "%" + resistance.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("frostResistance")),
                        floweringPeriod
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filter.getLighting() != null && !filter.getLighting().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String light : filter.getLighting()) {
                String floweringPeriod = "%" + light.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("lighting")),
                        floweringPeriod
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filter.getEvergreen() != null && !filter.getEvergreen().isEmpty()) {
            predicates.add(root.get("evergreen").in(filter.getEvergreen()));
        }

        if (filter.getFloweringPeriod() != null && !filter.getFloweringPeriod().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String period : filter.getFloweringPeriod()) {
                String floweringPeriod = "%" + period.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("floweringPeriod")),
                        floweringPeriod
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filter.getPlantType() != null && !filter.getPlantType().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String type : filter.getPlantType()) {
                String f1 = "%" + type.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("plantType")),
                        f1
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filter.getZoning() != null && !filter.getZoning().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String moisture : filter.getZoning()) {
                String zoning = "%" + moisture.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("zoning")),
                        zoning
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filter.getPh() != null && !filter.getPh().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String moisture : filter.getPh()) {
                String ph = "%" + moisture.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("ph")),
                        ph
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filter.getSoilMoisture() != null && !filter.getSoilMoisture().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String moisture : filter.getSoilMoisture()) {
                String floweringPeriod = "%" + moisture.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("soilMoisture")),
                        floweringPeriod
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filter.getHardy() != null && !filter.getHardy().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String nutrition : filter.getNutrition()) {
                String hardy = "%" + nutrition.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("hardy")),
                        hardy
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filter.getNutrition() != null && !filter.getNutrition().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (String nutrition : filter.getNutrition()) {
                String floweringPeriod = "%" + nutrition.toLowerCase() + "%";
                orPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nutrition")),
                        floweringPeriod
                ));
            }
            predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (filter.getSummerColor() != null && !filter.getSummerColor().isEmpty()) {
            List<Predicate> colorPredicates = filter.getSummerColor()
                    .stream()
                    .map(color -> color.substring(4))
                    .map(color -> criteriaBuilder.like(root.get("summerColor"), "%" + color + "%"))
                    .collect(Collectors.toList());

            predicates.add(criteriaBuilder.or(colorPredicates.toArray(new Predicate[0])));
        }

        if (filter.getAutumnColor() != null && !filter.getAutumnColor().isEmpty()) {
            List<Predicate> autumnColorPredicates = filter.getAutumnColor()
                    .stream()
                    .map(color -> color.substring(4))
                    .map(color -> criteriaBuilder.like(root.get("autumnColor"), "%" + color + "%"))
                    .collect(Collectors.toList());

            predicates.add(criteriaBuilder.or(autumnColorPredicates.toArray(new Predicate[0])));
        }

        if (filter.getFloweringColor() != null && !filter.getFloweringColor().isEmpty()) {
            List<Predicate> floweringColor = filter.getFloweringColor()
                    .stream()
                    .map(color -> color.substring(4))
                    .map(color -> criteriaBuilder.like(root.get("floweringColor"), "%" + color + "%"))
                    .collect(Collectors.toList());

            predicates.add(criteriaBuilder.or(floweringColor.toArray(new Predicate[0])));
        }

        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        return finalPredicate;
    }
}