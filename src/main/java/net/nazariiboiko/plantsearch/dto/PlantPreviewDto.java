package net.nazariiboiko.plantsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantPreviewDto {
    private Long id;
    private String name;
    private String latinName;
    private String image;
    private String sketch;
}
