package net.example.plantsearchrest.service;

import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.exception.JwtAuthenticationException;
import net.example.plantsearchrest.model.AuthResponse;

import java.util.List;
import java.util.Map;

public interface UserService {
    AuthResponse login(String username, String token, String refreshToken) throws JwtAuthenticationException;
    UserEntity register(UserEntity userEntity);
    List<UserDto> getAll();
    UserEntity findByLogin(String username);
    UserEntity findById(Long id);

    UserEntity findByEmail(String email);

    void delete(Long id);
    void update(UserDto entity);
    void setStatus(Long id, Status status);
    void activate(UserDto userDto, String code);
}