package net.nazariiboiko.plantsearch.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.nazariiboiko.plantsearch.dto.PlantPreviewDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Favourite [Users Only]", description = "Return list of user's favorites plants.")
public interface FavouriteApi {

    @Operation(summary = "Return list of favorites plants by user jwt token.")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Page<PlantPreviewDto>> getFavouritesByAccount(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size
    );

    @Operation(summary = "Add/remove plants to favorite list")
    @GetMapping("/{plantId}")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<Void> updateFavourite(@PathVariable Long plantId);

    @Operation(summary = "Check if a plant is liked by user.")
    @GetMapping("/check/{plantId}")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<Boolean> isLikedByUser(@PathVariable Long plantId);
}
