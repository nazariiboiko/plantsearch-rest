package net.example.plantsearchrest.api;

import io.swagger.annotations.*;

import net.example.plantsearchrest.dto.PlantPreviewDto;
import net.example.plantsearchrest.exception.NotFoundException;
import net.example.plantsearchrest.model.SinglePage;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.model.PlantFilterModel;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    SinglePage<PlantPreviewDto> getPlantList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size);

    @ApiOperation("Get plant by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Plant not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    PlantDto getPlantById(
            @ApiParam(value = "Plant ID")
            @PathVariable long id) throws NotFoundException;

    @ApiOperation("Get a random list of plants")
    @ApiResponse(code = 200, message = "OK")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/random")
    List<PlantPreviewDto> getRandomPlantList(
            @ApiParam(value = "Amount of random plants")
            @RequestParam(value = "amount", required = false, defaultValue = "1") int amount);

    @ApiOperation("Search all plants by name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    SinglePage<PlantPreviewDto> searchPlantsByName(
            @ApiParam(value = "keyword string")
            @RequestParam String keyword,
            @ApiParam(value = "Number of page")
            @RequestParam(value = "page", defaultValue = "0") int page,
            @ApiParam(value = "Size of page")
            @RequestParam(value = "size", defaultValue = "20") int size);

    @ApiOperation("Filter plants by criteria")
    @ApiResponse(code = 200, message = "OK")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/filter")
    SinglePage<PlantPreviewDto> filterPlants(
            @ApiParam(value = "Plant filter model")
            @RequestBody PlantFilterModel filter,
            @ApiParam(value = "Number of page")
            @RequestParam(value = "page", defaultValue = "0") int page,
            @ApiParam(value = "Size of page")
            @RequestParam(value = "size", defaultValue = "20") int size);

    @ApiOperation("Create a new plant")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
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
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Long> updatePlant(
            @ApiParam(value = "Plant DTO")
            @RequestPart PlantDto plantDto,
            @ApiParam(value = "Image of plant")
            @RequestPart(name = "image", required = false) MultipartFile image,
            @ApiParam(value = "Sketch of plant")
            @RequestPart(name = "sketch", required = false) MultipartFile sketch) throws IOException;

    @ApiOperation("Delete a plant")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Void> deletePlant(
            @ApiParam(value = "Plant ID")
            @RequestParam long id);
}
