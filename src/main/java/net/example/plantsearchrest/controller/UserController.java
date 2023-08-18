package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.api.UserApi;
import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.mapper.UserMapper;
import net.example.plantsearchrest.security.jwt.JwtUser;
import net.example.plantsearchrest.service.UserService;
import net.example.plantsearchrest.utils.UserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userList = userService.getAll().stream()
                .map(userMapper::mapEntityToDto)
                .collect(Collectors.toList());
        return userList;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserDto getUserById(@PathVariable("id") Long id) {
        UserDto user = userMapper.mapEntityToDto(userService.findById(id));
        return user;
    }

    @Override
    public ResponseEntity updateUser(@RequestBody UserDto userDto) {
        JwtUser user = UserUtil.getAuthUser();
        log.info("IN updateUser - user {}(id:{}) is trying to update user {}(id: {})",user.getLogin(), user.getId(), userDto.getLogin(), userDto.getId());
        userService.update(userDto);
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
