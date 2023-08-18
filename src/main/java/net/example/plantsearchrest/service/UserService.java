package net.example.plantsearchrest.service;

import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.exception.JwtAuthenticationException;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<Object, Object> login(String username, String token) throws JwtAuthenticationException;
    UserEntity register(UserEntity userEntity);
    List<UserEntity> getAll();
    UserEntity findByLogin(String username);
    UserEntity findById(Long id);
    void delete(Long id);
    void update(UserDto entity);
    void setStatus(Long id, Status status);
    void activate(UserDto userDto, String code);
}