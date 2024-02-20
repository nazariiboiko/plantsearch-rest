package net.nazariiboiko.plantsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantMarkerDto {
    private Long id;
    private Long plantId;
    private Long groupId;
    private int positionTop;
    private int positionLeft;
    private String plantName;
}
