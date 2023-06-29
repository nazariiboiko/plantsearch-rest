package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.entity.Favourite;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.mapper.PlantMapper;
import net.example.plantsearchrest.mapper.UserMapper;
import net.example.plantsearchrest.security.jwt.JwtUser;
import net.example.plantsearchrest.service.FavouriteService;
import net.example.plantsearchrest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final FavouriteService favouriteService;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final PlantMapper plantMapper = PlantMapper.INSTANCE;

    @GetMapping
    public List<UserDto> getAllUsers() {
        List<UserDto> userList = userService.getAll().stream()
                .map(userMapper::mapEntityToDto)
                .collect(Collectors.toList());

        log.info("in getAllUsers - return {} objects", userList.size());

        return userList;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Long id) {
        UserDto user = userMapper.mapEntityToDto(userService.findById(id));

        log.info("IN getUsetById - return user {}", user.getId());

        return user;
    }

    @GetMapping("/favourite")
    @PreAuthorize("isAuthenticated()")
    public List<PlantDto> getFavouritesById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser user = (JwtUser) authentication.getPrincipal();
        List<PlantDto> favourites = userService
                .findById(user.getId())
                .getFavourites().stream()
                .map(x -> plantMapper.mapEntityToDto(x.getPlant()))
                .collect(Collectors.toList());

        log.info("IN getFavouritesById - returned list of favourites of user {}", user.getId());

        return favourites;
    }

    @PostMapping("/like")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity changeLikeStatement(@RequestParam("id") Long plantId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser user = (JwtUser) authentication.getPrincipal();
        favouriteService.changeLikeStatement(plantId, user.getId());

        log.info("IN changeLikeStatement - user {} has marked plant {}", user.getLogin(), plantId);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity updateUser(@RequestBody UserDto userDto) {
        log.info("IN updateUser - trying to update user id {}", userDto.getId());

        UserEntity user = userMapper.mapDtoToEntity(userDto);
        userService.update(user);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity setStatus(
            @RequestParam int id,
            @RequestParam String status) {
        log.info("IN setStatus - user id {} has blocked", id);
        userService.setStatus((long) id, Status.valueOf(status));
        return ResponseEntity.ok(null);
    }
}
