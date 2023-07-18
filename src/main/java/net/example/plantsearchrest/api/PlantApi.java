package net.example.plantsearchrest.api;

import io.swagger.annotations.*;

import net.example.plantsearchrest.dto.PageDto;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.model.PlantFilterModel;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;

@Api(tags = "Plant management api")
@RequestMapping("/api/v1/plants")
public interface PlantApi {

    @ApiOperation("Get all Plants")
    @ApiResponse(code = 200, message = "OK")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    PageDto<PlantDto> getPlantList(@ApiIgnore("Ignored because swagger ui shows the wrong params")
                                Pageable pageable);

    @ApiOperation("Get plant by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Plant not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    PlantDto getPlantById(
            @ApiParam(value = "Plant ID")
            @PathVariable long id);

    @ApiOperation("Get a random list of plants")
    @ApiResponse(code = 200, message = "OK")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/random")
    List<PlantDto> getRandomPlantList(
            @ApiParam(value = "Amount of random plants")
            @RequestParam(value = "amount", required = false, defaultValue = "4") int amount);

    @ApiOperation("Search all plants by name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    PageDto<PlantDto> searchPlantsByName(
            @ApiParam(value = "keyword string")
            @RequestParam String keyword,
            @ApiIgnore("Ignored because swagger ui shows the wrong params")
            Pageable pageable);

    @ApiOperation("Filter plants by criteria")
    @ApiResponse(code = 200, message = "OK")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/filter")
    PageDto<PlantDto> filterPlants(
            @ApiParam(value = "Plant filter model")
            @RequestBody PlantFilterModel filter,
            @ApiIgnore("Ignored because swagger ui shows the wrong params")
            Pageable pageable);

    @ApiOperation("Create a new plant")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Long> createPlant(
            @ApiParam(value = "Plant DTO")
            @RequestPart("plantDto") PlantDto plantDto,
            @ApiParam(value = "Image of plant")
            @RequestPart(name = "image", required = false) MultipartFile image,
            @ApiParam(value = "Sketch of plant")
            @RequestPart(name = "sketch", required = false) MultipartFile sketch) throws IOException;

    @ApiOperation("Update a plant")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity updatePlant(
            @ApiParam(value = "Plant DTO")
            @RequestPart PlantDto plantDto,
            @ApiParam(value = "Image of plant")
            @RequestPart(name = "image", required = false) MultipartFile image,
            @ApiParam(value = "Sketch of plant")
            @RequestPart(name = "sketch", required = false) MultipartFile sketch) throws IOException;

    @ApiOperation("Delete a plant")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity deletePlant(
            @ApiParam(value = "Plant ID")
            @RequestParam long id);
}
