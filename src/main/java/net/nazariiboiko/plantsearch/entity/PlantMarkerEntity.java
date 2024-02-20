package net.nazariiboiko.plantsearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "plant_markers")
public class PlantMarkerEntity extends BaseEntity {
    @Column(name = "plant_id", nullable = false)
    private Long plantId;

    @Column(name = "position_top", nullable = false)
    private int positionTop;

    @Column(name = "position_left", nullable = false)
    private int positionLeft;

    @Column(name = "name", nullable = false)
    private String plantName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private PlantMarkerGroupEntity group;
}
