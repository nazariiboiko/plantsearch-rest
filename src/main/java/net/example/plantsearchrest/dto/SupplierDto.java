package net.example.plantsearchrest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.model.SinglePage;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SupplierDto implements Comparable<SupplierDto>{
    private Long id;
    private String name;
    private SinglePage<PlantPreviewDto> avaliablePlants;

    @Override
    public int compareTo(SupplierDto other) {
        return this.getId().compareTo(other.getId());
    }
}
