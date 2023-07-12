package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.api.UserApi;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.dto.UserDto;
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
public class UserController implements UserApi {

    private final UserService userService;
    private final FavouriteService favouriteService;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final PlantMapper plantMapper = PlantMapper.INSTANCE;

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userList = userService.getAll().stream()
                .map(userMapper::mapEntityToDto)
                .collect(Collectors.toList());

        log.info("in getAllUsers - return {} objects", userList.size());

        return userList;
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDto getUserByUsername(String username) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserDto getUserById(@PathVariable("id") Long id) {
        UserDto user = userMapper.mapEntityToDto(userService.findById(id));

        log.info("IN getUsetById - return user {}", user.getId());

        return user;
    }

    @Override
    public ResponseEntity updateUser(@RequestBody UserDto userDto) {
        log.info("IN updateUser - trying to update user id {}", userDto.getId());

        UserEntity user = userMapper.mapDtoToEntity(userDto);
        userService.update(user);
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity setStatus(
            @RequestParam int id,
            @RequestParam String status) {
        log.info("IN setStatus - user id {} has changed status to {}", id, status.toString());
        userService.setStatus((long) id, Status.valueOf(status));
        return ResponseEntity.ok(null);
    }
}
