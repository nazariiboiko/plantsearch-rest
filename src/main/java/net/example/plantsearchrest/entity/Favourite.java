package net.example.plantsearchrest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "favourite")
@AllArgsConstructor
@NoArgsConstructor
public class Favourite extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private PlantEntity plant;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
