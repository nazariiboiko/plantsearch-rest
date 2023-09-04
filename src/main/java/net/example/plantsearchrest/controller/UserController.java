package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.api.UserApi;
import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.exception.NotFoundException;
import net.example.plantsearchrest.model.SinglePage;
import net.example.plantsearchrest.security.jwt.JwtUser;
import net.example.plantsearchrest.service.UserService;
import net.example.plantsearchrest.utils.PageUtil;
import net.example.plantsearchrest.utils.UserUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;

    @Override
    public SinglePage<UserDto> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<UserDto> userList = userService.getAll();
        log.info("IN getAllUsers - return {} of users", userList.size());
        return PageUtil.create(userList, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public UserDto getUserByUsername(String username) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserDto getUserById(@PathVariable("id") Long id) throws NotFoundException {
        UserDto user = userService.findById(id);
        log.info("IN getUserById - return user {}(id:{})",user.getLogin(), user.getId());
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
