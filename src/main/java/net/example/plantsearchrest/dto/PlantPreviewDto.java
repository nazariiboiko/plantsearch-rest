package net.example.plantsearchrest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PlantPreviewDto implements Comparable<PlantPreviewDto> {
    private Long id;
    private String name;
    private String latinName;
    private String image;
    private String sketch;

    public PlantPreviewDto(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(PlantPreviewDto other) {
        return this.getId().compareTo(other.getId());
    }
}
