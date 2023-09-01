package net.example.plantsearchrest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.entity.Favourite;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.exception.NotFoundException;
import net.example.plantsearchrest.repository.FavouriteRepository;
import net.example.plantsearchrest.repository.PlantRepository;
import net.example.plantsearchrest.repository.UserRepository;
import net.example.plantsearchrest.service.FavouriteService;
import net.example.plantsearchrest.service.PlantService;
import net.example.plantsearchrest.service.UserService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRep;
    private final UserRepository userRepository;
    private final PlantRepository plantRepository;


    @Override
    public void changeLikeStatement(Long plantId, Long userId) throws NotFoundException {
        UserEntity user = userRepository.findById(userId).orElse(null);
        PlantEntity plant = plantRepository.findById(plantId).orElse(null);
        if(user != null && plant != null) {
            Favourite fav = favouriteRep.findByUserAndPlant(user, plant);
            if (fav == null) {
                fav = new Favourite(plant, user);
                favouriteRep.save(fav);
            } else {
                favouriteRep.delete(fav);
            }
        } else {
            throw new NotFoundException("Not found", "NOT_FOUND");
        }
    }
}
