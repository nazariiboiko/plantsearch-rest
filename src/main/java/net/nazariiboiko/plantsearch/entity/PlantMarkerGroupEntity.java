package net.nazariiboiko.plantsearch.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plant_marker_groups")
@EqualsAndHashCode(callSuper = true)
public class PlantMarkerGroupEntity extends BaseEntity {
    @Column(name = "src")
    private String src;

    @ToString.Exclude
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<PlantMarkerEntity> markers;
}
