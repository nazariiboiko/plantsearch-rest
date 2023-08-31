package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.api.AuthApi;
import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.exception.JwtAuthenticationException;
import net.example.plantsearchrest.exception.RegistryException;
import net.example.plantsearchrest.mapper.UserMapper;
import net.example.plantsearchrest.model.AuthRefreshRequest;
import net.example.plantsearchrest.model.AuthRequest;
import net.example.plantsearchrest.model.AuthResponse;
import net.example.plantsearchrest.security.jwt.JwtTokenProvider;
import net.example.plantsearchrest.service.UserService;
import net.example.plantsearchrest.utils.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UserService userService;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final JwtTokenProvider jwtTokenProvider;
    private final Messages messages;

    @Override
    public ResponseEntity<?> login(AuthRequest request, String lang) {
        try {
            AuthResponse response = jwtTokenProvider.authenticate(request.getLogin(), request.getPassword());
            log.info("IN login - user {} has logged in", request.getLogin());
            return ResponseEntity.ok(response);
        }
        catch (JwtAuthenticationException e) {
            String message = messages.getMessage(e.getMessageCode(), new Locale(lang));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @Override
    public ResponseEntity<?> register(UserDto userDto, String lang) {
        try {
            UserEntity user = userMapper.mapDtoToEntity(userDto);
            userService.register(user);
            log.info("IN register - user {} has been created", userDto.getLogin());
            String message = messages.getMessage("CONFIRM_EMAIL", new Locale(lang));
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (RegistryException e) {
            String message = messages.getMessage(e.getMessageCode(), new Locale(lang));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @Override
    public ResponseEntity<?> activate(UserDto userDto, String code, String lang) {
        try {
            userService.activate(userDto, code);
            log.info("IN activate - user {} has been activated", userDto.getLogin());
            return login(new AuthRequest(userDto.getLogin(), userDto.getPassword()), lang);
        } catch (RegistryException e) {
            String message = messages.getMessage(e.getMessageCode(), new Locale(lang));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @Override
    public ResponseEntity<?> refreshToken(AuthRefreshRequest request) {
        try {
            AuthResponse response = jwtTokenProvider.createTokenByRefreshToken(request.getRefreshToken());
            return ResponseEntity.ok().body(response);
        } catch (JwtAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
