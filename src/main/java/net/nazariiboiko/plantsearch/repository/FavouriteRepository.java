package net.nazariiboiko.plantsearch.repository;

import net.nazariiboiko.plantsearch.entity.FavouriteEntity;
import net.nazariiboiko.plantsearch.entity.PlantEntity;
import net.nazariiboiko.plantsearch.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteRepository extends JpaRepository<FavouriteEntity, Long> {
    FavouriteEntity findByUserIdAndPlantId(Long userId, Long plantId);
    FavouriteEntity findByUserAndPlant(UserEntity user, PlantEntity plant);
}