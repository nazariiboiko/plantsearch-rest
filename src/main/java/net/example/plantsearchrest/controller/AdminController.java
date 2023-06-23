package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.mapper.PlantMapper;
import net.example.plantsearchrest.mapper.UserMapper;
import net.example.plantsearchrest.service.PlantService;
import net.example.plantsearchrest.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final PlantService plantService;
    private final UserService userService;

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.getAll().stream()
                .map(UserMapper.INSTANCE::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/plants")
    public List<PlantDto> getAllPlants() {
        return plantService.getAll().stream()
                .map(PlantMapper.INSTANCE::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
