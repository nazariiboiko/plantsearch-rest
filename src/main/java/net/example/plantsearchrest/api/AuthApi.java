package net.example.plantsearchrest.api;

import io.swagger.annotations.*;
import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.exception.JwtAuthenticationException;
import net.example.plantsearchrest.exception.NotFoundException;
import net.example.plantsearchrest.exception.RegistryException;
import net.example.plantsearchrest.model.AuthRefreshRequest;
import net.example.plantsearchrest.model.AuthRequest;
import net.example.plantsearchrest.model.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "Auth management api")
@RequestMapping("/api/v1/auth")
public interface AuthApi {
    @ApiOperation("Log in")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
    })
    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(@ApiParam(value = "AuthRequest DTO")
                         @RequestBody AuthRequest requestDto) throws NotFoundException, JwtAuthenticationException;

    @ApiOperation("Sign up")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/register")
    ResponseEntity<String> register(@ApiParam(value = "User DTO")
                            @RequestBody UserDto userDto) throws RegistryException;

    @ApiOperation("Activate account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/activate")
    ResponseEntity<AuthResponse> activate(@ApiParam(value = "User DTO")
                            @RequestBody UserDto userDto,
                            @ApiParam(value = "Activation code")
                            @RequestParam String code) throws RegistryException, NotFoundException, JwtAuthenticationException;

    @ApiOperation("Create a new jwt token via refreshToken")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/refresh")
    ResponseEntity<AuthResponse> refreshToken(
            @ApiParam(value = "Refresh token")
            @RequestBody AuthRefreshRequest request) throws JwtAuthenticationException, NotFoundException;

}
