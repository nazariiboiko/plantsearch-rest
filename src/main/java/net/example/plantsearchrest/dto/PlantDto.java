package net.example.plantsearchrest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PlantDto implements Comparable<PlantDto>{
    private Long id;
    private String name;
    private String latinName;
    private String height;
    private String habitus;
    private String growthRate;
    private String color;
    private String summerColor;
    private String autumnColor;
    private String floweringColor;
    private String frostResistance;
    private String sketch;
    private String image;
    private String recommendation;
    private String lighting;
    private String evergreen;
    private String floweringPeriod;
    private String plantType;
    private String zoning;
    private String ph;
    private String soilMoisture;
    private String hardy;
    private String nutrition;

    @Override
    public int compareTo(PlantDto other) {
        return this.getId().compareTo(other.getId());
    }
}
