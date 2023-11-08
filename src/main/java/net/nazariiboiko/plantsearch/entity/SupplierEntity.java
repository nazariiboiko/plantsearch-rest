package net.nazariiboiko.plantsearch.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Entity
@Table(name = "supplier")
public class SupplierEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "supplier_plant",
            joinColumns = @JoinColumn(name = "supplier_id"),
            inverseJoinColumns = @JoinColumn(name = "plant_id")
    )
    private List<PlantEntity> availablePlants;

    public List<PlantEntity> paginatePlants(int pageNumber, int pageSize) {
    //    return PageUtil.create(avaliablePlants, pageNumber, pageSize);
        return null;
    }
}