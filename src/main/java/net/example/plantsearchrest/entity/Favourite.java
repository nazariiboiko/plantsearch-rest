package net.example.plantsearchrest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.springframework.context.annotation.Lazy;

@Data
@Entity
@Table(name = "favourite")
public class Favourite extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private PlantEntity plant;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
