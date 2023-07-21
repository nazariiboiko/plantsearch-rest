package net.example.plantsearchrest.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class BaseEntity implements Comparable<BaseEntity>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Override
    public int compareTo(BaseEntity other) {
        return this.getId().compareTo(other.getId());
    }
}
