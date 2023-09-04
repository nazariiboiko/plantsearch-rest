package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.api.AuthApi;
import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.exception.JwtAuthenticationException;
import net.example.plantsearchrest.exception.NotFoundException;
import net.example.plantsearchrest.exception.RegistryException;
import net.example.plantsearchrest.model.AuthRefreshRequest;
import net.example.plantsearchrest.model.AuthRequest;
import net.example.plantsearchrest.model.AuthResponse;
import net.example.plantsearchrest.security.jwt.JwtTokenProvider;
import net.example.plantsearchrest.service.UserService;
import net.example.plantsearchrest.utils.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final Messages messages;

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest request) throws NotFoundException, JwtAuthenticationException {
        AuthResponse response = jwtTokenProvider.authenticate(request.getLogin(), request.getPassword());
        log.info("IN login - user {} has logged in", request.getLogin());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> register(UserDto userDto) throws RegistryException {
        userService.register(userDto);
        log.info("IN register - user {} has been created", userDto.getLogin());
        String message = messages.getMessage("CONFIRM_EMAIL");
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @Override
    public ResponseEntity<AuthResponse> activate(UserDto userDto, String code) throws RegistryException, NotFoundException, JwtAuthenticationException {
        userService.activate(userDto, code);
        AuthResponse response = jwtTokenProvider.authenticate(userDto.getLogin(), userDto.getPassword());
        log.info("IN activate - user {} has been activated", userDto.getLogin());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AuthResponse> refreshToken(AuthRefreshRequest request) throws JwtAuthenticationException, NotFoundException {
        AuthResponse response = jwtTokenProvider.createTokenByRefreshToken(request.getRefreshToken());
        return ResponseEntity.ok().body(response);
    }
}
