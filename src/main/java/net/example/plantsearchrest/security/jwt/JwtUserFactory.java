package net.example.plantsearchrest.security.jwt;

import lombok.NoArgsConstructor;
import net.example.plantsearchrest.entity.Role;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class JwtUserFactory {
    public static JwtUser create(UserEntity userEntity) {
        return new JwtUser(userEntity.getId(),
                userEntity.getLogin(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getStatus().equals(Status.ACTIVE),
                mapToGrantedAuthorities(List.of(userEntity.getRole())) );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());
    }
}
