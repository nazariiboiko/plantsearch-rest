package net.example.plantsearchrest.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.entity.Role;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.exception.RegistryException;
import net.example.plantsearchrest.mapper.PlantMapper;
import net.example.plantsearchrest.mapper.UserMapper;
import net.example.plantsearchrest.repository.UserRepository;
import net.example.plantsearchrest.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity register(UserEntity userEntity) {
        if(userRepository.findByLogin(userEntity.getLogin()) != null)
            throw new RegistryException("User already exists");
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(Role.USER);
        userEntity.setStatus(Status.ACTIVE);
        UserEntity registeredUserEntity = userRepository.save(userEntity);
        log.info("IN register - user: {} successfully registered", registeredUserEntity);
        return registeredUserEntity;
    }

    @Override
    public List<UserEntity> getAll() {
        List<UserEntity> userEntityList = userRepository.findAll();
        log.info("IN getAll - {} users found", userEntityList.size());
        return userEntityList;
    }

    @Override
    public List<UserEntity> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public UserEntity findByLogin(String username) {
        UserEntity userEntity = userRepository.findByLogin(username);
        log.info("IN findByUsername - users: {} found by username: {}", userEntity, username);
        return userEntity;
    }

    @Override
    public UserEntity findById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);

        if(userEntity == null) {
            log.warn("IN findById - user: {] not found", id);
            return null;
        }

        log.info("IN findById - user found by id: {}", id, userEntity);
        return userEntity;
    }

    @Override
    public void delete(Long id) {
        log.info("IN delete - user: {} successfully deleted", id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(UserEntity entity) {
        log.info("IN update - updated id: {}", entity.getId());
        UserEntity user = userRepository.findById(entity.getId()).orElseThrow();
        UserMapper.INSTANCE.updateUserEntity(entity, user);
    }

    @Override
    @Transactional
    public void setStatus(Long id, Status status) {
        log.info("IN block - blocked {} user", id);
        userRepository.findById(id)
                .orElseThrow()
                .setStatus(status);
    }


}
