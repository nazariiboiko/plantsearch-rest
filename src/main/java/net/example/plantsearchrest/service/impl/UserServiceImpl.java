package net.example.plantsearchrest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.entity.Role;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.exception.RegistryException;
import net.example.plantsearchrest.repository.UserRepository;
import net.example.plantsearchrest.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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
    public UserEntity findByUsername(String username) {
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

        log.info("IN findById - user: {} found by id: {}", id, userEntity);
        return null;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user: {} successfully deleted", id);
    }
}
