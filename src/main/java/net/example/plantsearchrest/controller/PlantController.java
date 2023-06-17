package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.mapper.PlantMapper;
import net.example.plantsearchrest.model.PlantFilterModel;
import net.example.plantsearchrest.service.PlantService;
import net.example.plantsearchrest.utils.PlantFilterQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/plant")
@RequiredArgsConstructor
public class PlantController {

    private final PlantService plantService;
    private final PlantMapper plantMapper = PlantMapper.INSTANCE;

    @GetMapping
    public List<PlantDto> plantList() {
        log.info("IN plantList | return all objects");
        return plantService.getAll().stream()
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
    public List<PlantDto> plantListByCriterias(@RequestBody PlantFilterModel filter) {
        String query = PlantFilterQueryBuilder.buildQuery(filter);

        log.info("IN plantListByCriterias - created next query {}", query);

        return plantService.executeQuery(query, filter.getSize(), filter.getPage()).stream()
                .map(plantMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
