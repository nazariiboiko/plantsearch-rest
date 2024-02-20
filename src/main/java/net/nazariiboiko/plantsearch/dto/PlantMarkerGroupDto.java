package net.nazariiboiko.plantsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantMarkerGroupDto {
    private Long id;
    private String src;
    private List<PlantMarkerDto> markers;
}
