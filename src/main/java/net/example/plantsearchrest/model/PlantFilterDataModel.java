package net.example.plantsearchrest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlantFilterDataModel {
    private String name; //UNSUPPORED

    @JsonProperty("habitus")
    private List<String> habitus;

    @JsonProperty("growthRate")
    private List<String> growthRate;

    @JsonProperty("color")
    private List<String> color;

    @JsonProperty("summerColor")
    private List<String> summerColor;

    @JsonProperty("autumnColor")
    private List<String> autumnColor;

    @JsonProperty("floweringColor")
    private List<String> floweringColor;

    @JsonProperty("frostResistance")
    private List<String> frostResistance;

    @JsonProperty("recommendation")
    private List<String> recommendation;

    @JsonProperty("lighting")
    private List<String> lighting;

    @JsonProperty("evergreen")
    private List<String> evergreen;

    @JsonProperty("floweringPeriod")
    private List<String> floweringPeriod;

    @JsonProperty("plantType")
    private List<String> plantType;

    @JsonProperty("zoning")
    private List<String> zoning;

    @JsonProperty("ph")
    private List<String> ph;

    @JsonProperty("soilMoisture")
    private List<String> soilMoisture;

    @JsonProperty("hardy")
    private List<String> hardy;

    @JsonProperty("nutrition")
    private List<String> nutrition;
}
