package net.example.plantsearchrest.entity;

import javax.persistence.*;
import lombok.Data;
import net.example.plantsearchrest.model.SinglePage;
import net.example.plantsearchrest.utils.PageUtil;

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
    private List<PlantEntity> avaliablePlants;

    public SinglePage<PlantEntity> paginatePlants(int pageNumber, int pageSize) {
        return PageUtil.create(avaliablePlants, pageNumber, pageSize);
    }
}
