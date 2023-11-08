package net.nazariiboiko.plantsearch.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nazariiboiko.plantsearch.entity.FavouriteEntity;
import net.nazariiboiko.plantsearch.entity.PlantEntity;
import net.nazariiboiko.plantsearch.entity.UserEntity;
import net.nazariiboiko.plantsearch.exception.PlantNotFoundException;
import net.nazariiboiko.plantsearch.exception.UserNotFoundException;
import net.nazariiboiko.plantsearch.repository.FavouriteRepository;
import net.nazariiboiko.plantsearch.repository.PlantRepository;
import net.nazariiboiko.plantsearch.repository.UserRepository;
import net.nazariiboiko.plantsearch.service.FavouriteService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRep;
    private final UserRepository userRepository;
    private final PlantRepository plantRepository;


    @Override
    public void changeLikeStatement(Long plantId, Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        PlantEntity plant = plantRepository.findById(plantId).orElse(null);
        if(user != null && plant != null) {
            FavouriteEntity fav = favouriteRep.findByUserAndPlant(user, plant);
            if (fav == null) {
                fav = new FavouriteEntity(plant, user);
                favouriteRep.save(fav);
            } else {
                favouriteRep.delete(fav);
            }
        } else {
            throw new PlantNotFoundException("Not found", "NOT_FOUND");
        }
    }

    @Override
    public boolean getLikeStatement(Long plantId, Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found", "NOT_FOUND"));
        PlantEntity plant = plantRepository.findById(plantId).orElseThrow(() -> new PlantNotFoundException("Not found", "NOT_FOUND"));
        return favouriteRep.findByUserAndPlant(user, plant) == null ? false : true;
    }
}
