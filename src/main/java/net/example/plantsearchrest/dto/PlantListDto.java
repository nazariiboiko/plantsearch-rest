package net.example.plantsearchrest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PlantListDto {
    private List<PlantDto> data;
    private int pageSize;
    private int pageNumber;
    private int totalSize;
}
