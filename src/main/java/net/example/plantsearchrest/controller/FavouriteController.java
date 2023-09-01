package net.example.plantsearchrest.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.api.FavouritesApi;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.dto.PlantPreviewDto;
import net.example.plantsearchrest.exception.NotFoundException;
import net.example.plantsearchrest.exception.ServiceException;
import net.example.plantsearchrest.mapper.PlantMapper;
import net.example.plantsearchrest.security.jwt.JwtUser;
import net.example.plantsearchrest.service.FavouriteService;
import net.example.plantsearchrest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FavouriteController implements FavouritesApi {

    private final UserService userService;
    private final FavouriteService favouriteService;

    @Override
    public ResponseEntity<List<PlantPreviewDto>> getFavouritesByUserId(Long userId) throws NotFoundException {
        List<PlantPreviewDto> result = userService.getFavouritesByUserId(userId);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<PlantPreviewDto>> getFavouritesByAccount() throws NotFoundException {
        JwtUser user = userService.getPrincipal();
        List<PlantPreviewDto> result = userService.getFavouritesByUserId(user.getId());
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity changeFavouriteStatement(Long plantId) throws NotFoundException {
        JwtUser user = userService.getPrincipal();
        favouriteService.changeLikeStatement(plantId, user.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
