package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.api.AuthApi;
import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.exception.JwtAuthenticationException;
import net.example.plantsearchrest.exception.RegistryException;
import net.example.plantsearchrest.mapper.UserMapper;
import net.example.plantsearchrest.model.AuthRequest;
import net.example.plantsearchrest.security.jwt.JwtTokenProvider;
import net.example.plantsearchrest.service.UserService;
import net.example.plantsearchrest.utils.Messages;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Map;

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
            String username = request.getLogin();
            String token = jwtTokenProvider.authenticate(username, request.getPassword());
            Map<Object, Object> jwtToken = userService.login(username, token);
            log.info("IN login - user {} has logged in", jwtToken.get("username"));
            return ResponseEntity.ok(jwtToken);
        }
        catch (JwtAuthenticationException e) {
            String message = messages.getMessage(e.getMessageCode(), new Locale(lang));
            return ResponseEntity.badRequest().body(message);
        }
    }

    @Override
    public ResponseEntity<?> register(UserDto userDto, String lang) {
        try {
            UserEntity user = userMapper.mapDtoToEntity(userDto);
            userService.register(user);
            log.info("IN register - user {} has been created", userDto.getLogin());
            return login(new AuthRequest(userDto.getLogin(), userDto.getPassword()), lang);
        } catch (RegistryException e) {
            String message = messages.getMessage(e.getMessageCode(), new Locale(lang));
            return ResponseEntity.badRequest().body(message);
        }
    }
}
