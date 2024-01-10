package net.nazariiboiko.plantsearch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nazariiboiko.plantsearch.api.AuthApi;
import net.nazariiboiko.plantsearch.dto.UserDto;
import net.nazariiboiko.plantsearch.model.AuthRequest;
import net.nazariiboiko.plantsearch.model.AuthResponse;
import net.nazariiboiko.plantsearch.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthApi {

    private final UserService userService;

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {
        AuthResponse response = userService.login(authRequest);
        log.info("IN login - user:{} has logged in", authRequest.getLogin());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AuthResponse> register(UserDto userDto) {
        AuthResponse response = userService.register(userDto);
        log.info("IN register - user:{} has registered", userDto.getLogin());
        return ResponseEntity.ok(response);
    }
}
