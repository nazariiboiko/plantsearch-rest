package net.nazariiboiko.plantsearch.service;

import net.nazariiboiko.plantsearch.dto.PlantPreviewDto;
import net.nazariiboiko.plantsearch.dto.UserDto;
import net.nazariiboiko.plantsearch.model.AuthRequest;
import net.nazariiboiko.plantsearch.model.AuthResponse;
import net.nazariiboiko.plantsearch.security.jwt.JwtUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    AuthResponse login(AuthRequest dto);

    AuthResponse register(UserDto dto);

    UserDto findByLogin(String username);
    UserDto findByEmail(String email);
    UserDto findByLoginOrEmail(String login);

    Page<UserDto> getAllUsers(Pageable pageable);

    Page<PlantPreviewDto> getFavouritesByUserId(Long userId, Pageable pageable);

    JwtUser getPrincipal();
}
