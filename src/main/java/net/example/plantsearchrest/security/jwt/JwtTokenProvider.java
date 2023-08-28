package net.example.plantsearchrest.security.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.entity.Role;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.exception.JwtAuthenticationException;
import net.example.plantsearchrest.model.AuthResponse;
import net.example.plantsearchrest.service.UserService;
import net.example.plantsearchrest.utils.RSAKeyUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsServ;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}") //10h
    private Integer validityInMiliseconds;

    @Value("${jwt.refresh.size}")
    private int keySize;

    @Value("${jwt.refresh.privateKey}")
    private String privateKey;

    @Value("${jwt.refresh.publicKey}")
    private String publicKey;

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

    public String createRefreshToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);

        Date createdDate = new Date();
        Date validity = new Date(createdDate.getTime() + validityInMiliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(createdDate)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.RS256, RSAKeyUtil.createPrivateKey(privateKey))
                .compact();
    }

    public AuthResponse createTokenByRefreshToken(String oldRefreshToken) throws JwtAuthenticationException {
        Claims claims = getClaimsFromRefreshToken(oldRefreshToken);
        UserEntity user = userService.findByLogin(claims.getSubject());
        if(validateRefreshToken(oldRefreshToken, claims, user)) {
            log.info("User {}(id:{}) has renewed his jwt token.", user.getLogin(), user.getId());
            return userService.login(user.getLogin(), createToken(user.getLogin(), user.getRole()), createRefreshToken(user.getLogin()));
        }
        return null;
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

    public boolean validateRefreshToken(String token, Claims claims, UserEntity user) {
        try {
            if(!user.getRefreshToken().equals(token) || claims.getExpiration().before(new Date())) {
                throw new JwtAuthenticationException("Token expired.", "TOKEN_EXPIRED");
            }
            return true;

        } catch (Exception e) {
            return false;
        }
    }
    public boolean validateToken(String token)  {
        Claims claims = getClaimsFromToken(token);

        if (claims.getExpiration().before(new Date())) {
            return false;
        } else return true;
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Claims getClaimsFromRefreshToken(String token) {
        return Jwts.parser()
                .setSigningKey(RSAKeyUtil.createPublicKey(publicKey))
                .parseClaimsJws(token)
                .getBody();
    }

    public AuthResponse authenticate(String loginOrEmail, String password) throws JwtAuthenticationException {
        UserEntity userEntity;
        if(isValidEmail(loginOrEmail)) {
            userEntity = userService.findByEmail(loginOrEmail);
        } else {
            userEntity = userService.findByLogin(loginOrEmail);
        }


        if(userEntity == null) {
            throw new JwtAuthenticationException("Invalid username or password", "INVALID_CREDENTIALS");
        }

        if(userEntity.getStatus().equals(Status.ACTIVE)) {
            if(passwordEncoder.matches(password, userEntity.getPassword())) {
                return userService.login(userEntity.getLogin(), createToken(userEntity.getLogin(), userEntity.getRole()), createRefreshToken(userEntity.getLogin()));
            } else throw new JwtAuthenticationException("Invalid username or password", "INVALID_CREDENTIALS");
        } else throw new JwtAuthenticationException("User is blocked", "USER_BLOCKED");
    }

    public static boolean isValidEmail(String email) {
        String emailPattern = "\\S+@\\S+\\.\\S+";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

