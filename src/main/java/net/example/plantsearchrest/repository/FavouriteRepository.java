package net.example.plantsearchrest.repository;

import net.example.plantsearchrest.entity.Favourite;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    Favourite findByUserIdAndPlantId(Long userId, Long plantId);
    Favourite findByUserAndPlant(UserEntity user, PlantEntity plant);
}
