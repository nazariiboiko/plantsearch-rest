package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.api.AuthApi;
import net.example.plantsearchrest.dto.AuthRequestDto;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.exception.JwtAuthenticationException;
import net.example.plantsearchrest.mapper.UserMapper;
import net.example.plantsearchrest.security.jwt.JwtTokenProvider;
import net.example.plantsearchrest.service.UserService;
import net.example.plantsearchrest.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    public ResponseEntity login(@RequestBody AuthRequestDto requestDto) {
        try {
            String username = requestDto.getLogin();
            String token = jwtTokenProvider.authenticate(username, requestDto.getPassword());
            Map<Object, Object> response = new HashMap<>() {{
                put("username", username);
                put("token", token);
            }};

            log.info("IN login - user {} has logged in", username);

            return ResponseEntity.ok(response);
        }
        catch (JwtAuthenticationException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @Override
    public ResponseEntity register(@RequestBody UserDto userDto) {
        UserEntity user = userMapper.mapDtoToEntity(userDto);
        userService.register(user);

        log.info("IN register - user {} has been registered", userDto.getLogin());

        return login(new AuthRequestDto(userDto.getLogin(), userDto.getPassword()));
    }
}
