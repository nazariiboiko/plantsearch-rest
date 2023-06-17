package net.example.plantsearchrest.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.example.plantsearchrest.entity.Role;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.exception.JwtAuthenticationException;
import net.example.plantsearchrest.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsServ;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}") //1h
    private Integer validityInMiliseconds;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username, Role role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role.toString());

        Date createdDate = new Date();
        Date validity = new Date(createdDate.getTime() + validityInMiliseconds);

        return  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(createdDate)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsServ.loadUserByUsername(getUsernameByToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsernameByToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
    public boolean validateToken(String token)  {
        Claims claims = getClaimsFromToken(token);

        if (claims.getExpiration().before(new Date())) {
            throw new RuntimeException("Token expired");
        } else return true;
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String authenticate(String username, String password) throws JwtAuthenticationException {
        UserEntity userEntity = userService.findByUsername(username);

        if(userEntity == null) {
            throw new JwtAuthenticationException("User not found");
        }

        if(userEntity.getStatus().equals(Status.ACTIVE)) {
            if(passwordEncoder.matches(password, userEntity.getPassword())) {
                return createToken(username, userEntity.getRole());
            } else throw new JwtAuthenticationException("Invalid password");
        } else throw new JwtAuthenticationException("User is blocked");
    }
}
