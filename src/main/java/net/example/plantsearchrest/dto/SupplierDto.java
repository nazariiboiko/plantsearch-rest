package net.example.plantsearchrest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.example.plantsearchrest.entity.PlantEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SupplierDto {
    private Long id;
    private String name;
    private List<PlantEntity> avaliablePlants;
}
