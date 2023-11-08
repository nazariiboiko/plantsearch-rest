package net.nazariiboiko.plantsearch.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nazariiboiko.plantsearch.entity.UserEntity;
import net.nazariiboiko.plantsearch.exception.JwtAuthenticationException;
import net.nazariiboiko.plantsearch.repository.UserRepository;
import net.nazariiboiko.plantsearch.security.jwt.JwtUserFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRep;
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userRep.findByLogin(username)
                .orElseThrow(() -> new JwtAuthenticationException("User not found", "service.user.not_found"));

        return JwtUserFactory.create(userEntity);
    }
}