package net.example.plantsearchrest.service;

import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.entity.UserEntity;

import java.awt.print.Pageable;
import java.util.List;

public interface UserService {
    UserEntity register(UserEntity userEntity);
    List<UserEntity> getAll();
    List<UserEntity> getAll(Pageable pageable);
    UserEntity findByLogin(String username);
    UserEntity findById(Long id);

    void create();

    void delete(Long id);
    void update(UserEntity entity);
    void setStatus(Long id, Status status);
}