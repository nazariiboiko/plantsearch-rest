package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.dto.PlantListDto;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.mapper.PlantMapper;
import net.example.plantsearchrest.model.PlantFilterModel;
import net.example.plantsearchrest.service.PlantService;
import net.example.plantsearchrest.utils.PlantFilterQueryBuilder;
import net.example.plantsearchrest.utils.PlantListDtoBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plants")
public class PlantController {

    private final PlantService plantService;
    private final PlantMapper plantMapper = PlantMapper.INSTANCE;

    @GetMapping
    public List<PlantDto> plantList(
            @RequestParam(value = "page", required = false, defaultValue ="0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        log.info("IN plantList | return page {} in total {} objects", page, size);

        return plantService.getAll(PageRequest.of(page, size)).stream()
                .map(plantMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PlantDto plantByIndex(@PathVariable long id) {
        log.info("IN plantByIndex | return object with {} id", id);
        return plantMapper.mapEntityToDto(plantService.getById(id));
    }

    @GetMapping("/random")
    public List<PlantDto> plantListRandom(@RequestParam int amount) {
        log.info("IN plantListRandom | return {} objects", amount);
        return plantService.getRandom(amount).stream()
                .map(plantMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<PlantDto> plantListTop4ByMatchingName(@RequestParam String query) {
        List<PlantDto> list = plantService.findTop4ByName(query).stream()
                .map(plantMapper::mapEntityToDto)
                .collect(Collectors.toList());

        log.info("IN searchSimilarByName | return {} objects for {} query", list.size(), query);

        return list;
    }

    @PostMapping("/filter")
    public PlantListDto plantListByCriterias(@RequestBody PlantFilterModel filter) {
        String query = PlantFilterQueryBuilder.buildQuery(filter);

        log.info("IN plantListByCriterias - created next query {}", query);

        List<PlantDto> list = plantService.executeQuery(query).stream()
                .map(plantMapper::mapEntityToDto)
                .collect(Collectors.toList());

        return PlantListDtoBuilder.create(list, filter.getPage(), filter.getSize());
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity updatePlant(@RequestBody PlantDto plantDto) {
        log.info("IN updatePlant - trying to update plant id {}", plantDto.getId());
        PlantEntity plant = plantMapper.mapDtoToEntity(plantDto);
        plantService.update(plant);
        return ResponseEntity.ok(null);
    }
}
