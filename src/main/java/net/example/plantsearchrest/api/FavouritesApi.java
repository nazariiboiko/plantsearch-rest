package net.example.plantsearchrest.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.example.plantsearchrest.dto.PlantDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Favourites management api")
@RequestMapping("/api/v1/favourites")
public interface FavouritesApi {
    @ApiOperation("Get user favourites")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    List<PlantDto> getFavouritesByAccount();

    @ApiOperation("Change favourite statement")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/like")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<Void> changeFavouriteStatement(
            @ApiParam(value = "Plant ID", required = true) @RequestParam("id") Long plantId);
}
