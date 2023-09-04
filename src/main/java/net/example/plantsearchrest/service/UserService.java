package net.example.plantsearchrest.service;

import net.example.plantsearchrest.dto.PlantPreviewDto;
import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.exception.JwtAuthenticationException;
import net.example.plantsearchrest.exception.NotFoundException;
import net.example.plantsearchrest.model.AuthResponse;
import net.example.plantsearchrest.security.jwt.JwtUser;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface UserService {
    JwtUser getPrincipal();
    AuthResponse login(String username, String token, String refreshToken) throws JwtAuthenticationException, NotFoundException;
    UserDto register(UserDto userDto);
    List<UserDto> getAll();
    UserDto findByLogin(String username) throws NotFoundException;
    UserEntity findEntityByLogin(String username) throws NotFoundException;
    UserDto findById(Long id) throws NotFoundException;
    UserEntity findEntityById(Long id) throws NotFoundException;
    UserDto findByEmail(String email) throws NotFoundException;
    UserEntity findEntityByEmail(String email) throws NotFoundException;
    void delete(Long id);
    void update(UserDto entity);
    void setStatus(Long id, Status status);
    void activate(UserDto userDto, String code) throws NotFoundException;
    List<PlantPreviewDto> getFavouritesByUserId(Long userId) throws NotFoundException;
}