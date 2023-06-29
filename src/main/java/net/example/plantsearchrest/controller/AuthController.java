package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @PostMapping("/login")
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

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDto userDto) {
        UserEntity user = userMapper.mapDtoToEntity(userDto);
        userService.register(user);

        log.info("IN register - user {} has been registered", userDto.getLogin());

        return login(new AuthRequestDto(userDto.getLogin(), userDto.getPassword()));
    }

    @GetMapping("register")
    public ResponseEntity<String> checkLogin(@RequestParam String login) {
        if (userService.findByLogin(login) == null) {
            return ResponseEntity.ok("Login is available");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login is already taken");
        }
    }
}
