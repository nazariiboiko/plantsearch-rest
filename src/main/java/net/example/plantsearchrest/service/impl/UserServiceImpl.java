package net.example.plantsearchrest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.dto.PlantPreviewDto;
import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.entity.Role;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.exception.JwtAuthenticationException;
import net.example.plantsearchrest.exception.NotFoundException;
import net.example.plantsearchrest.exception.RegistryException;
import net.example.plantsearchrest.mapper.PlantMapper;
import net.example.plantsearchrest.mapper.UserMapper;
import net.example.plantsearchrest.model.AuthResponse;
import net.example.plantsearchrest.repository.UserRepository;
import net.example.plantsearchrest.security.jwt.JwtUser;
import net.example.plantsearchrest.service.MailSender;
import net.example.plantsearchrest.service.UserService;
import net.example.plantsearchrest.utils.ConfirmCodeUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSender mailSender;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final PlantMapper plantMapper = PlantMapper.INSTANCE;

    @Override
    public JwtUser getPrincipal() {
        return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    @Transactional
    public AuthResponse login(String username, String token, String refreshToken) throws JwtAuthenticationException, NotFoundException {
        Optional<UserEntity> opt = userRepository.findByLogin(username);
        if(opt.isPresent()) {
            UserEntity userFromDb = opt.get();
            userFromDb.setLastLogin(LocalDateTime.now());
            userFromDb.setRefreshToken(refreshToken);
            userRepository.save(userFromDb);

            AuthResponse response = new AuthResponse(token, refreshToken);
            return response;
        } else {
            throw new NotFoundException("User not found", "NOT_FOUND");
        }
    }

    @Override
    @Transactional
    public UserDto register(UserDto userDto) {
        UserEntity userEntity = userMapper.mapDtoToEntity(userDto);

        if(userRepository.findByLogin(userEntity.getLogin()) != null)
            throw new RegistryException("User already exists","USER_ALREADY_EXISTS");
        if(userRepository.findByEmail(userEntity.getEmail()) != null)
            throw new RegistryException("Email has already been taken", "EMAIL_TAKEN");

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(Role.USER);
        userEntity.setStatus(Status.NOT_ACTIVE);
        userEntity.setRegistrationDate(LocalDate.now());

        int code = ConfirmCodeUtil.generate6DigitCode();
        userEntity.setActivateCode(String.valueOf(code));

        UserEntity registeredUserEntity = userRepository.save(userEntity);
        log.info("IN register - user: {} successfully registered", registeredUserEntity);
        String confirmMessage = "Hello " + userEntity.getLogin() + ", thank you for trying out our service. Your email confirmation code is: " + code + ". We're excited to have you on board!";
        mailSender.send(userEntity.getEmail(), "Підтвердження пароля", confirmMessage);
        return userMapper.mapEntityToDto(registeredUserEntity);
    }

    @Override
    public List<UserDto> getAll() {
        List<UserEntity> userEntityList = userRepository.findAll();
        return userEntityList.stream()
                .map(userMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findByLogin(String username) throws NotFoundException {
        Optional<UserEntity> entityFromBd = userRepository.findByLogin(username);
        if(entityFromBd.isPresent()) {
            return userMapper.mapEntityToDto(entityFromBd.get());
        } else throw new NotFoundException("Not found", "NOT_FOUND");
    }

    @Override
    public UserEntity findEntityByLogin(String username) throws NotFoundException {
        Optional<UserEntity> entityFromBd = userRepository.findByLogin(username);
        if(entityFromBd.isPresent()) {
            return entityFromBd.get();
        } else throw new NotFoundException("Not found", "NOT_FOUND");
    }

    @Override
    public UserDto findById(Long id) throws NotFoundException {
        Optional<UserEntity> entityFromBd = userRepository.findById(id);
        if(entityFromBd.isPresent()) {
            return userMapper.mapEntityToDto(entityFromBd.get());
        } else throw new NotFoundException("Not found", "NOT_FOUND");
    }

    @Override
    public UserEntity findEntityById(Long id) throws NotFoundException {
        Optional<UserEntity> entityFromBd = userRepository.findById(id);
        if(entityFromBd.isPresent()) {
            return entityFromBd.get();
        } else throw new NotFoundException("Not found", "NOT_FOUND");
    }

    @Override
    public UserDto findByEmail(String email) throws NotFoundException {
        Optional<UserEntity> entityFromBd = userRepository.findByEmail(email);
        if(entityFromBd.isPresent()) {
            return userMapper.mapEntityToDto(entityFromBd.get());
        } else throw new NotFoundException("Not found", "NOT_FOUND");
    }

    @Override
    public UserEntity findEntityByEmail(String email) throws NotFoundException {
        Optional<UserEntity> entityFromBd = userRepository.findByEmail(email);
        if(entityFromBd.isPresent()) {
            return entityFromBd.get();
        } else throw new NotFoundException("Not found", "NOT_FOUND");
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user: {} successfully deleted", id);
    }

    @Override
    @Transactional
    public void update(UserDto dto) {
        log.info("IN update - updated id: {}", dto.getId());
        Optional<UserEntity> user = userRepository.findById(dto.getId());
        if(user.isPresent()) {
            UserEntity entity = userMapper.mapDtoToEntity(dto);
            UserMapper.INSTANCE.updateUserEntity(entity, user.get());
        }
    }

    @Override
    @Transactional
    public void setStatus(Long id, Status status) {
        log.info("IN setStatus - blocked {} user", id);
        userRepository.findById(id)
                .orElseThrow(null)
                .setStatus(status);
    }

    @Override
    public void activate(UserDto userDto, String code) throws NotFoundException, RegistryException {
        Optional<UserEntity> opt = userRepository.findByLogin(userDto.getLogin());
        if(opt.isPresent()) {
            UserEntity userFromDB = opt.get();
            if (userFromDB != null && userFromDB.getActivateCode().equals(code)) {
                userFromDB.setStatus(Status.ACTIVE);
                userRepository.save(userFromDB);
            } else {
                throw new RegistryException("Activation code does not match", "INVALID_CODE");
            }
        } else {
            throw new NotFoundException("User not found.", "USER_NOT_FOUND");
        }
    }

    @Override
    public List<PlantPreviewDto> getFavouritesByUserId(Long userId) throws NotFoundException {
        Optional<UserEntity> userFromDb = userRepository.findById(userId);

        if(userFromDb.isPresent()) {
            List<PlantPreviewDto> dtoList = userFromDb.get().getFavourites().stream()
                    .map(x -> plantMapper.mapEntityToPreviewDto(x.getPlant()))
                    .collect(Collectors.toList());
            return dtoList;
        } else {
            throw new NotFoundException("User not found.", "USER_NOT_FOUND");
        }
    }
}
