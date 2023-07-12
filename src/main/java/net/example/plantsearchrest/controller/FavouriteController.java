package net.example.plantsearchrest.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.api.FavouritesApi;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.mapper.PlantMapper;
import net.example.plantsearchrest.security.jwt.JwtUser;
import net.example.plantsearchrest.service.FavouriteService;
import net.example.plantsearchrest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FavouriteController implements FavouritesApi {

    private final UserService userService;
    private final FavouriteService favouriteService;
    private final PlantMapper plantMapper = PlantMapper.INSTANCE;

    @Override
    public List<PlantDto> getFavouritesByAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser user = (JwtUser) authentication.getPrincipal();
        List<PlantDto> favourites = userService
                .findById(user.getId())
                .getFavourites().stream()
                .map(x -> plantMapper.mapEntityToDto(x.getPlant()))
                .collect(Collectors.toList());

        log.info("IN getFavouritesById - returned list of favourites of user {}", user.getId());

        return favourites;
    }

    @Override
    public ResponseEntity changeFavouriteStatement(@RequestParam("id") Long plantId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser user = (JwtUser) authentication.getPrincipal();
        favouriteService.changeLikeStatement(plantId, user.getId());

        log.info("IN changeLikeStatement - user {} has marked plant {}", user.getLogin(), plantId);

        return ResponseEntity.ok(null);
    }
}
