package net.example.plantsearchrest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.exception.JwtAuthenticationException;
import net.example.plantsearchrest.exception.RegistryException;
import net.example.plantsearchrest.model.AuthRequest;
import net.example.plantsearchrest.model.AuthResponse;
import net.example.plantsearchrest.security.jwt.JwtTokenProvider;
import net.example.plantsearchrest.service.UserService;
import net.example.plantsearchrest.utils.Messages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser
@ActiveProfiles("dev")
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    private String AUTH_URL = "http://localhost:8085/api/v1/auth";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private Messages messages;

    @Test
    public void testLogin_Success() throws Exception {
        AuthRequest request = new AuthRequest("username", "password");

        AuthResponse authResponse = new AuthResponse("accessToken", "refreshToken");
        when(jwtTokenProvider.authenticate(anyString(), anyString())).thenReturn(authResponse);

        mockMvc.perform(post(AUTH_URL + "/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("accessToken"))
                .andExpect(jsonPath("$.refresh_token").value("refreshToken"));

        verify(jwtTokenProvider).authenticate(eq("username"), eq("password"));
    }

    @Test
    public void testLogin_Failure() throws Exception {
        AuthRequest request = new AuthRequest("username", "password");

        when(jwtTokenProvider.authenticate(anyString(), anyString()))
                .thenThrow(new JwtAuthenticationException("Authentication failed","error.message"));

        when(messages.getMessage(eq("error.message")))
                .thenReturn("Authentication failed");

        mockMvc.perform(post(AUTH_URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Authentication failed"));

        verify(jwtTokenProvider).authenticate(eq("username"), eq("password"));
    }

    @Test
    public void testRegister_Success() throws Exception {
        UserDto dto = new UserDto().toBuilder()
                .login("user47lover")
                .email("truemail@g.c")
                .password("hardpassword")
                .build();

        when(userService.register(any(UserDto.class))).thenReturn(new UserDto());
        when(messages.getMessage(eq("CONFIRM_EMAIL")))
                .thenReturn("Please confirm your email.");

        mockMvc.perform(post(AUTH_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Please confirm your email."));
    }

    @Test
    public void testRegister_Failure() throws Exception {
        UserDto dto = new UserDto().toBuilder()
                .login("user47lover")
                .email("truemail@g.c")
                .password("hardpassword")
                .build();

        when(userService.register(any(UserDto.class))).thenThrow(new RegistryException("User already exists","USER_ALREADY_EXISTS"));
        when(messages.getMessage(eq("USER_ALREADY_EXISTS")))
                .thenReturn("User already exists.");

        mockMvc.perform(post(AUTH_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User already exists."));
    }

    @Test
    public void testActivate_Success() throws Exception {
        UserDto dto = new UserDto().toBuilder()
                .login("user47lover")
                .email("truemail@g.c")
                .password("hardpassword")
                .build();

        String activateCode = "123456";

        mockMvc.perform(post(AUTH_URL + "/activate?code="+activateCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("accessToken"))
                .andExpect(jsonPath("$.refresh_token").value("refreshToken"));

    }

    @Test
    public void testActivate_Failure() throws Exception {
        UserDto dto = new UserDto().toBuilder()
                .login("user47lover")
                .email("truemail@g.c")
                .password("hardpassword")
                .build();

        doThrow(new RegistryException("Activation code does not match", "INVALID_CODE")).when(userService).activate(any(),anyString());
        when(messages.getMessage(eq("INVALID_CODE")))
                .thenReturn("Activation code does not match");

        mockMvc.perform(post(AUTH_URL + "/activate?code=123456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Activation code does not match"));
    }

    public static String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
