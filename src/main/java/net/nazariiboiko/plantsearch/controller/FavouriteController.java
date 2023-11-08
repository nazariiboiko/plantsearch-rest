package net.nazariiboiko.plantsearch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class FavouriteController {
    private final UserService userService;
    private final FavouriteService favouriteService;

    @GetMapping()
    public ResponseEntity<Page<PlantPreviewDto>> getFavouritesByAccount(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size
    ) {
        JwtUser user = userService.getPrincipal();
        Pageable pageable = PageRequest.of(page, size);
        Page<PlantPreviewDto> result = userService.getFavouritesByUserId(user.getId(), pageable);
        log.info("int getFavouritesByAccount - return ");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{plantId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> updateFavourite(@PathVariable Long plantId) {
        JwtUser user = userService.getPrincipal();
        favouriteService.changeLikeStatement(plantId, user.getId());
        log.info("");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check/{plantId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> isLikedByUser(@PathVariable Long plantId) {
        JwtUser user = userService.getPrincipal();
        boolean result = favouriteService.getLikeStatement(plantId, user.getId());
        log.info("IN isLikedByUser");
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }
}
