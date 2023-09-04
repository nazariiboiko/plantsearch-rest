package net.example.plantsearchrest.api;

import io.swagger.annotations.*;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.dto.PlantPreviewDto;
import net.example.plantsearchrest.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Favourites management api")
@RequestMapping("/api/v1/favourites")
public interface FavouritesApi {

    @ApiOperation("Get user's favourites by an id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<PlantPreviewDto>> getFavouritesByUserId(@PathVariable Long userId) throws NotFoundException;

    @ApiOperation("Get user's favourites based on a jwt-token")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"),
    })
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<List<PlantPreviewDto>> getFavouritesByAccount() throws NotFoundException;

    @ApiOperation("Change favourite statement based on a jwt-token")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
    })
    @PostMapping("/like")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<Void> changeFavouriteStatement(
            @ApiParam(value = "Plant ID", required = true)
            @RequestParam("id") Long plantId) throws NotFoundException;
}
