package net.example.plantsearchrest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.entity.Favourite;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.repository.FavouriteRepository;
import net.example.plantsearchrest.service.FavouriteService;
import net.example.plantsearchrest.service.PlantService;
import net.example.plantsearchrest.service.UserService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRep;
    private final UserService userService;
    private final PlantService plantService;


    @Override
    public void changeLikeStatement(Long plantId, Long userId) {
        UserEntity user = userService.findById(userId);
        PlantEntity plant = plantService.getEntityById(plantId);
        Favourite fav = favouriteRep.findByUserAndPlant(user, plant);
        if(fav == null) {
            fav = new Favourite();
            fav.setUser(user);
            fav.setPlant(plant);
            favouriteRep.save(fav);   //like
        } else {
            favouriteRep.delete(fav); //remove like
        }
    }
}
