package net.example.plantsearchrest.utils;

import io.micrometer.common.util.StringUtils;
import net.example.plantsearchrest.model.PlantFilterDataModel;
import net.example.plantsearchrest.model.PlantFilterModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlantFilterQueryBuilder {

    public static String buildQuery(PlantFilterModel filter) {

            PlantFilterDataModel filterDataModel = filter.getData();

            StringBuilder query = new StringBuilder("SELECT p FROM PlantEntity p WHERE 1=1 ");

            List<String> conditions = new ArrayList<>();

            if(filterDataModel.getName() != null && filterDataModel.getName().length() > 2) {
                if (Character.UnicodeBlock.of(filterDataModel.getName().charAt(0)) == Character.UnicodeBlock.CYRILLIC) {
                    if (!StringUtils.isEmpty(filterDataModel.getName())) {
                        conditions.add("name LIKE '%" + filterDataModel.getName() + "%'");
                    }
                } else if (Character.UnicodeBlock.of(filterDataModel.getName().charAt(0)) == Character.UnicodeBlock.BASIC_LATIN) {
                    if (!StringUtils.isEmpty(filterDataModel.getName())) {
                        conditions.add("p.latinName LIKE '%" + filterDataModel.getName() + "%'");
                    }
                }
            }

            if (filterDataModel.getHabitus() != null && !filterDataModel.getHabitus().isEmpty()) {
                conditions.add("p.habitus IN ('" + String.join("', '", filterDataModel.getHabitus()) + "')");
            }

            if (filterDataModel.getGrowthRate() != null && !filterDataModel.getGrowthRate().isEmpty()) {
                conditions.add("p.growthRate IN ('" + String.join("', '", filterDataModel.getGrowthRate()) + "')");
            }

            if (filterDataModel.getColor() != null && !filterDataModel.getColor().isEmpty()) {
                conditions.add("p.color IN ('" + String.join("', '", filterDataModel.getColor()) + "')");
            }

            if (filterDataModel.getFrostResistance() != null && !filterDataModel.getFrostResistance().isEmpty()) {
                conditions.add("p.frostResistance IN ('" + String.join("', '", filterDataModel.getFrostResistance()) + "')");
            }

            if (filterDataModel.getRecommendation() != null && !filterDataModel.getRecommendation().isEmpty()) {
                conditions.add("p.recommendation IN ('" + String.join("', '", filterDataModel.getRecommendation()) + "')");
            }

            if (filterDataModel.getLighting() != null && !filterDataModel.getLighting().isEmpty()) {
                conditions.add("p.lighting IN ('" + String.join("', '", filterDataModel.getLighting()) + "')");
            }

            if (filterDataModel.getEvergreen() != null && !filterDataModel.getEvergreen().isEmpty()) {
                conditions.add("p.evergreen IN ('" + String.join("', '", filterDataModel.getEvergreen()) + "')");
            }

            if (filterDataModel.getFloweringPeriod() != null && !filterDataModel.getFloweringPeriod().isEmpty()) {
                conditions.add("p.floweringPeriod LIKE '%" + String.join("%' OR p.floweringPeriod LIKE '%", filterDataModel.getPh()) + "%'");
            }

            if (filterDataModel.getPlantType() != null && !filterDataModel.getPlantType().isEmpty()) {
                conditions.add("p.plantType IN ('" + String.join("', '", filterDataModel.getPlantType()) + "')");
            }

            if (filterDataModel.getZoning() != null && !filterDataModel.getZoning().isEmpty()) {
                conditions.add("p.zoning IN ('" + String.join("', '", filterDataModel.getZoning()) + "')");
            }

            if (filterDataModel.getPh() != null && !filterDataModel.getPh().isEmpty()) {
                conditions.add("p.ph LIKE '%" + String.join("%' OR p.ph LIKE '%", filterDataModel.getPh()) + "%'");
            }

            if (filterDataModel.getSoilMoisture() != null && !filterDataModel.getSoilMoisture().isEmpty()) {
                conditions.add("p.soilMoisture IN ('" + String.join("', '", filterDataModel.getSoilMoisture()) + "')");
            }

            if (filterDataModel.getHardy() != null && !filterDataModel.getHardy().isEmpty()) {
                conditions.add("p.hardy IN ('" + String.join("', '", filterDataModel.getHardy()) + "')");
            }

            if (filterDataModel.getNutrition() != null && !filterDataModel.getNutrition().isEmpty()) {
                conditions.add("p.nutrition IN ('" + String.join("', '", filterDataModel.getNutrition()) + "')");
            }

            if (filterDataModel.getSummerColor() != null && !filterDataModel.getSummerColor().isEmpty()) {
                List<String> subdividedColors = filterDataModel.getSummerColor()
                        .stream()
                        .map(color -> color.substring(4))
                        .collect(Collectors.toList());

                conditions.add("p.summerColor LIKE '%" + String.join("%' OR p.summerColor LIKE '%", subdividedColors) + "%'");
            }

            if (filterDataModel.getAutumnColor() != null && !filterDataModel.getAutumnColor().isEmpty()) {
                List<String> subdividedColors = filterDataModel.getAutumnColor()
                        .stream()
                        .map(color -> color.substring(4))
                        .collect(Collectors.toList());

                conditions.add("p.autumnColor LIKE '%" + String.join("%' OR p.autumnColor LIKE '%", subdividedColors) + "%'");
            }

            if (filterDataModel.getFloweringColor() != null && !filterDataModel.getFloweringColor().isEmpty()) {
                List<String> subdividedColors = filterDataModel.getFloweringColor()
                        .stream()
                        .map(color -> color.substring(4))
                        .collect(Collectors.toList());

                conditions.add("p.floweringColor LIKE '%" + String.join("%' OR p.floweringColor LIKE '%", subdividedColors) + "%'");
            }

            if(conditions.size() != 0)
                query.append(" AND ").append(String.join(" AND ", conditions));

            return query.toString();
        }

}
