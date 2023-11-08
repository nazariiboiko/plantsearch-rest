package net.nazariiboiko.plantsearch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nazariiboiko.plantsearch.dto.UserDto;
import net.nazariiboiko.plantsearch.model.AuthRequest;
import net.nazariiboiko.plantsearch.model.AuthResponse;
import net.nazariiboiko.plantsearch.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        AuthResponse response = userService.login(authRequest);
        log.info("IN login - user:{} has logged in", authRequest.getLogin());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDto userDto) {
        AuthResponse response = userService.register(userDto);
        log.info("IN register - user:{} has registered", userDto.getLogin());
        return ResponseEntity.ok(response);
    }
}
