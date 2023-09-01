package net.example.plantsearchrest.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.exception.NotFoundException;
import net.example.plantsearchrest.repository.UserRepository;
import net.example.plantsearchrest.security.jwt.JwtUser;
import net.example.plantsearchrest.security.jwt.JwtUserFactory;
import net.example.plantsearchrest.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByLogin(username).orElse(null);

        if(userEntity == null)
            throw new UsernameNotFoundException("User with username "+username+" not found");

        JwtUser jwtUser = JwtUserFactory.create(userEntity);
        return jwtUser;
    }
}