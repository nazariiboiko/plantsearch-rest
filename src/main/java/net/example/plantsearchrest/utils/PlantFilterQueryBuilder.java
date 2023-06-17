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

            //1=1 if criterias are empty;
            StringBuilder query = new StringBuilder("SELECT p FROM PlantEntity p WHERE 1=1 ");

            List<String> conditions = new ArrayList<>();

            if (!StringUtils.isEmpty(filterDataModel.getName())) {
                conditions.add("name = '" + filterDataModel.getName() + "'");
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
                conditions.add("p.floweringPeriod IN ('" + String.join("', '", filterDataModel.getFloweringPeriod()) + "')");
            }

            if (filterDataModel.getPlantType() != null && !filterDataModel.getPlantType().isEmpty()) {
                conditions.add("p.plantType IN ('" + String.join("', '", filterDataModel.getPlantType()) + "')");
            }

            if (filterDataModel.getZoning() != null && !filterDataModel.getZoning().isEmpty()) {
                conditions.add("p.zoning IN ('" + String.join("', '", filterDataModel.getZoning()) + "')");
            }

            if (filterDataModel.getPh() != null && !filterDataModel.getPh().isEmpty()) {
                conditions.add("p.ph IN ('" + String.join("', '", filterDataModel.getPh()) + "')");
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

            System.out.println(conditions.size());
            if(conditions.size() != 0)
                query.append(" AND ").append(String.join(" AND ", conditions));

            return query.toString();
        }

}

/*
    SELECT * FROM plants
         WHERE habitus IN ('дельтовидна')
         AND color LIKE '%жовт%' OR color LIKE '%зелен%'
         LIMIT 20 OFFSET 0


    @JsonProperty("summerColor")
    private List<String> summerColor;

    @JsonProperty("autumnColor")
    private List<String> autumnColor;

    @JsonProperty("floweringColor")
    private List<String> floweringColor;

    */