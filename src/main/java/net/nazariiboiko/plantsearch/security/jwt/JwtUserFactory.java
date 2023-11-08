package net.nazariiboiko.plantsearch.security.jwt;

import lombok.NoArgsConstructor;
import net.nazariiboiko.plantsearch.entity.UserEntity;
import net.nazariiboiko.plantsearch.enums.Role;
import net.nazariiboiko.plantsearch.enums.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class JwtUserFactory {
    public static JwtUser create(UserEntity user) {
        return new JwtUser(user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getStatus().equals(Status.ACTIVE),
                mapToGrantedAuthorities(List.of(user.getRole())) );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());
    }
}