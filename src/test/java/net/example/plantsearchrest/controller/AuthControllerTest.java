package net.example.plantsearchrest.controller;

import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.entity.Role;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.exception.JwtAuthenticationException;
import net.example.plantsearchrest.exception.RegistryException;
import net.example.plantsearchrest.model.AuthRequest;
import net.example.plantsearchrest.security.jwt.JwtTokenProvider;
import net.example.plantsearchrest.service.UserService;
import net.example.plantsearchrest.utils.Messages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private Messages messages;

    @InjectMocks
    private AuthController authController;

    public static String login = "admin";
    public static String password = "admin";
    public static String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsIlhdCI6MTY5MDQzOTQ5NiwiZXhwIjoxNjkwNDc1NDk2fQ.iM4kAJODMsDOCv4FCxn0r3It5osvs2sHmFjuremUwGI";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() throws Exception {
        AuthRequest request = new AuthRequest(login, password);
        String lang = "en";
        Map<Object, Object> res = new HashMap<>() {{
            put("username", login);
            put("token", token);
        }};

        when(jwtTokenProvider.authenticate(login, password)).thenReturn(token);
        when(userService.login(login, token)).thenReturn(res);

        // Act
        ResponseEntity<?> response = authController.login(request, lang);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void testLogin_Failure_WrongCredentials() throws JwtAuthenticationException {
        AuthRequest request = new AuthRequest(login, password);
        String lang = "en";
        String errorMessage = "Invalid credentials.";
        String messageCode = "INVALID_CREDENTIALS";

        when(jwtTokenProvider.authenticate(login, password)).thenThrow(new JwtAuthenticationException(errorMessage, messageCode));
        when(messages.getMessage(messageCode, new Locale(lang))).thenReturn("Invalid credentials.");

        // Act
        ResponseEntity<?> response = authController.login(request, lang);

        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(errorMessage, response.getBody());
    }

    @Test
    void testRegister_Success() {
        UserDto userDto = new UserDto(null, "admin@gmail.com", "admin", "admin", Role.ADMIN, Status.ACTIVE, LocalDateTime.now(), LocalDateTime.now());
        String lang = "en";
        String messageCode = "CONFIRM_EMAIL";
        String messageText = "Please confirm your email.";

        when(messages.getMessage(messageCode, new Locale(lang))).thenReturn(messageText);

        // Act
        ResponseEntity<?> response = authController.register(userDto, lang);

        // Assert
        Assertions.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(messageText, response.getBody());
    }

    @Test
    void testRegister_Failure_UserExists() {
        UserDto userDto = new UserDto(null, "admin@gmail.com", "admin", "admin", Role.ADMIN, Status.ACTIVE, LocalDateTime.now(), LocalDateTime.now());
        String lang = "en";
        String messageText = "User already exists.";
        String messageCode = "USER_ALREADY_EXISTS";

        doThrow(new RegistryException(messageText, messageCode)).when(userService).register(any(UserEntity.class));
        when(messages.getMessage(messageCode, new Locale(lang))).thenReturn(messageText);

        // Act
        ResponseEntity<?> response = authController.register(userDto, lang);

        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(messageText, response.getBody());
    }

    @Test
    void testActivate_Success() throws JwtAuthenticationException {
        UserDto userDto = new UserDto(null, "admin@gmail.com", "admin", "admin", Role.ADMIN, Status.ACTIVE, LocalDateTime.now(), LocalDateTime.now());
        String activationCode = "123456";
        String lang = "en";
        Map<Object, Object> jwtToken = new HashMap<>() {{
            put("username", login);
            put("token", token);
        }};

        when(jwtTokenProvider.authenticate(userDto.getLogin(), userDto.getPassword())).thenReturn(token);
        when(userService.login(userDto.getLogin(), token)).thenReturn(jwtToken);

        // Act
        ResponseEntity<?> response = authController.activate(userDto, activationCode, lang);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void testActivate_Failure_InvalidCode() {
        // Arrange
        UserDto userDto = new UserDto();
        String invalidActivationCode = "123456";
        String lang = "en";
        String messageText = "Activation code does not match.";
        String messageCode = "INVALID_CODE";

        doThrow(new RegistryException(messageText, messageCode)).when(userService).activate(any(UserDto.class), eq(invalidActivationCode));
        when(messages.getMessage(messageCode, new Locale(lang))).thenReturn(messageText);

        // Act
        ResponseEntity<?> response = authController.activate(userDto, invalidActivationCode, lang);

        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(messageText, response.getBody());
    }
}