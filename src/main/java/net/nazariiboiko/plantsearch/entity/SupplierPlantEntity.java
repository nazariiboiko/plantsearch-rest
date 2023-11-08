package net.nazariiboiko.plantsearch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "supplier_plant")
public class SupplierPlantEntity extends BaseEntity{

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "plant_id")
    private Long plantId;
}