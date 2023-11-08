package net.nazariiboiko.plantsearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "favourite")
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private PlantEntity plant;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
