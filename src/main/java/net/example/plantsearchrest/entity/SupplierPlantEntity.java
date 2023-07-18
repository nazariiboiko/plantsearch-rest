package net.example.plantsearchrest.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "supplier_plant")
public class SupplierPlantEntity extends BaseEntity{

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "plant_id")
    private Long plantId;
}
