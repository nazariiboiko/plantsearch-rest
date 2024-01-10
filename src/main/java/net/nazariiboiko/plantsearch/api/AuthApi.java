package net.nazariiboiko.plantsearch.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.nazariiboiko.plantsearch.dto.UserDto;
import net.nazariiboiko.plantsearch.model.AuthRequest;
import net.nazariiboiko.plantsearch.model.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Authentication", description = "User authentication")
@RequestMapping("/api/v1/auth")
public interface AuthApi {

    @Operation(summary = "Login to get jwt token", description = "Authenticate user and return jwt token.")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest);

    @Operation(summary = "Register a new user", description = "Create a new user account.")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthResponse> register(@RequestBody UserDto userDto);
}
