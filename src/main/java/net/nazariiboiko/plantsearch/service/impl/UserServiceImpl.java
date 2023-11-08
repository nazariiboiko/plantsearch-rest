package net.nazariiboiko.plantsearch.service.impl;

import lombok.RequiredArgsConstructor;
import net.nazariiboiko.plantsearch.dto.PlantPreviewDto;
import net.nazariiboiko.plantsearch.dto.UserDto;
import net.nazariiboiko.plantsearch.entity.UserEntity;
import net.nazariiboiko.plantsearch.enums.Role;
import net.nazariiboiko.plantsearch.enums.Status;
import net.nazariiboiko.plantsearch.exception.JwtAuthenticationException;
import net.nazariiboiko.plantsearch.exception.UserAlreadyExistsException;
import net.nazariiboiko.plantsearch.exception.UserNotFoundException;
import net.nazariiboiko.plantsearch.mapper.PlantMapper;
import net.nazariiboiko.plantsearch.mapper.UserMapper;
import net.nazariiboiko.plantsearch.model.AuthRequest;
import net.nazariiboiko.plantsearch.model.AuthResponse;
import net.nazariiboiko.plantsearch.repository.UserRepository;
import net.nazariiboiko.plantsearch.security.jwt.JwtTokenProvider;
import net.nazariiboiko.plantsearch.security.jwt.JwtUser;
import net.nazariiboiko.plantsearch.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRep;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    public AuthResponse login(AuthRequest dto) {
        UserDto userDto = findByLoginOrEmail(dto.getLogin());
        if(authenticate(dto.getPassword(), userDto)) {
            return new AuthResponse(jwtTokenProvider.createToken(userDto.getLogin(), userDto.getRole()));
        } else throw new JwtAuthenticationException("Authentication failed", "service.user.not_authenticated");
    }

    @Override
    public AuthResponse register(UserDto dto) {
        if(userRep.existsByEmailOrLogin(dto.getEmail(), dto.getLogin()))
            throw new UserAlreadyExistsException("User already exists", "service.user.already_exists");

        UserEntity entityToCreate = new UserEntity();
        entityToCreate.setLogin(dto.getLogin());
        entityToCreate.setEmail(dto.getEmail());
        entityToCreate.setPassword(passwordEncoder.encode(dto.getPassword()));
        entityToCreate.setStatus(Status.ACTIVE);
        entityToCreate.setRole(Role.USER);

        userRep.save(entityToCreate);
        return new AuthResponse(jwtTokenProvider.createToken(entityToCreate.getLogin(), entityToCreate.getRole()));

    }

    @Override
    public UserDto findByLogin(String username) {
        UserEntity entity = userRep.findByLogin(username)
                .orElseThrow(() -> new UserNotFoundException("User not found", "service.user.not_found"));
        return userMapper.mapEntityToDto(entity);
    }

    @Override
    public UserDto findByEmail(String email) {
        UserEntity entity = userRep.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found", "service.user.not_found"));
        return userMapper.mapEntityToDto(entity);
    }

    @Override
    public UserDto findByLoginOrEmail(String login) {
        return (isValidEmail(login)) ? findByEmail(login) : findByLogin(login);
    }

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRep.findAll(pageable).map(userMapper::mapEntityToDto);
    }

    @Override
    public Page<PlantPreviewDto> getFavouritesByUserId(Long userId, Pageable pageable) {
        UserEntity userFromDb = userRep.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found", ""));
        List<PlantPreviewDto> dtoList = userFromDb.getFavourites().stream()
                .map(x -> PlantMapper.INSTANCE.mapEntityToPreviewDto(x.getPlant()))
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, dtoList.size());
    }

    @Override
    public JwtUser getPrincipal() {
        return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private boolean authenticate(String password, UserDto userDto) {
        if(userDto.getStatus().equals(Status.ACTIVE)) {
            if(passwordEncoder.matches(password, userDto.getPassword())) {
                return true;
            } else throw new JwtAuthenticationException("Invalid username or password", "service.user.invalid_credentials");
        } else throw new JwtAuthenticationException("User is blocked", "service.user.user_blocked");
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "\\S+@\\S+\\.\\S+";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
