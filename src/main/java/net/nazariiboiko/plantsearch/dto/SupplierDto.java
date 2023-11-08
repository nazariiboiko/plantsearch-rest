package net.nazariiboiko.plantsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SupplierDto implements Comparable<SupplierDto>{
    private Long id;
    private String name;
    private Page<PlantPreviewDto> availablePlants;

    @Override
    public int compareTo(SupplierDto other) {
        return this.getId().compareTo(other.getId());
    }
}