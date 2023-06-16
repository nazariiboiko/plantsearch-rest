package net.example.plantsearchrest.service;

import net.example.plantsearchrest.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity register(UserEntity userEntity);
    List<UserEntity> getAll();
    UserEntity findByUsername(String username);
    UserEntity findById(Long id);
    void delete(Long id);
}