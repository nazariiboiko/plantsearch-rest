package net.nazariiboiko.plantsearch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nazariiboiko.plantsearch.api.UserApi;
import net.nazariiboiko.plantsearch.dto.UserDto;
import net.nazariiboiko.plantsearch.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public  ResponseEntity<Page<UserDto>> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, Math.min(50, size));
        Page<UserDto> dto = userService.getAllUsers(pageable);
        log.info("IN getAllUsers - return {} of users", dto.getSize());
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Void> changeStatus(Long id){
        return null;
    }
}
