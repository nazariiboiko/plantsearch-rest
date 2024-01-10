package net.nazariiboiko.plantsearch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nazariiboiko.plantsearch.api.FavouriteApi;
import net.nazariiboiko.plantsearch.dto.PlantPreviewDto;
import net.nazariiboiko.plantsearch.security.jwt.JwtUser;
import net.nazariiboiko.plantsearch.service.FavouriteService;
import net.nazariiboiko.plantsearch.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractList;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/favourites")
public class FavouriteController implements FavouriteApi {
    private final UserService userService;
    private final FavouriteService favouriteService;

    @Override
    public ResponseEntity<Page<PlantPreviewDto>> getFavouritesByAccount(
             int page, int size
    ) {
        JwtUser user = userService.getPrincipal();
        Pageable pageable = PageRequest.of(page, size);
        Page<PlantPreviewDto> result = userService.getFavouritesByUserId(user.getId(), pageable);
        log.info("IN getFavouritesByAccount - return ");
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> updateFavourite(Long plantId) {
        JwtUser user = userService.getPrincipal();
        favouriteService.changeLikeStatement(plantId, user.getId());
        log.info("IN updateFavourite - ");
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Boolean> isLikedByUser(Long plantId) {
        JwtUser user = userService.getPrincipal();
        boolean result = favouriteService.getLikeStatement(plantId, user.getId());
        log.info("IN isLikedByUser - ");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
