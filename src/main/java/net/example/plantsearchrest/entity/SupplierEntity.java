package net.example.plantsearchrest.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "supplier")
public class SupplierEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "supplier_plant",
            joinColumns = @JoinColumn(name = "supplier_id"),
            inverseJoinColumns = @JoinColumn(name = "plant_id")
    )
    private List<PlantEntity> avaliablePlants;
}
