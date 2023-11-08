package net.nazariiboiko.plantsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.common.util.StringUtils;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlantFilterModel {
    @JsonProperty("name")
    private String name;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(name)) sb.append("name='").append(name).append("', ");
        if(habitus != null) sb.append("habitus=").append(habitus).append(", ");
        if(growthRate != null) sb.append("growthRate=").append(growthRate).append(", ");
        if(color != null) sb.append("color=").append(color).append(", ");
        if(summerColor != null) sb.append("summerColor=").append(summerColor).append(", ");
        if(autumnColor != null) sb.append("autumnColor=").append(autumnColor).append(", ");
        if(floweringColor != null) sb.append("floweringColor=").append(floweringColor).append(", ");
        if(frostResistance != null) sb.append("frostResistance=").append(frostResistance).append(", ");
        if(recommendation != null) sb.append("recommendation=").append(recommendation).append(", ");
        if(lighting != null) sb.append("lighting=").append(lighting).append(", ");
        if(evergreen != null) sb.append("evergreen=").append(evergreen).append(", ");
        if(floweringPeriod != null) sb.append("floweringPeriod=").append(floweringPeriod).append(", ");
        if(plantType != null) sb.append("plantType=").append(plantType).append(", ");
        if(zoning != null) sb.append("zoning=").append(zoning).append(", ");
        if(ph != null) sb.append("ph=").append(ph).append(", ");
        if(soilMoisture != null) sb.append("soilMoisture=").append(soilMoisture).append(", ");
        if(hardy != null) sb.append("hardy=").append(hardy).append(", ");
        if(nutrition != null) sb.append("nutrition=").append(nutrition);
        return sb.toString();
    }
}