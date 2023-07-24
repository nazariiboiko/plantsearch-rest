package net.example.plantsearchrest.api;

import io.swagger.annotations.*;
import net.example.plantsearchrest.model.AuthRequest;
import net.example.plantsearchrest.dto.UserDto;
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
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/login")
    ResponseEntity login(@ApiParam(value = "AuthRequest DTO")
                         @RequestBody AuthRequest requestDto,
                         @ApiParam(value = "language")
                         @RequestParam(defaultValue = "en") String language);

    @ApiOperation("Sign up")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/register")
    ResponseEntity register(@ApiParam(value = "User DTO")
                            @RequestBody UserDto userDto,
                            @ApiParam(value = "language")
                            @RequestParam(defaultValue = "en") String language);

    @ApiOperation("Activate account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/activate")
    ResponseEntity activate(@ApiParam(value = "User DTO")
                            @RequestBody UserDto userDto,
                            @ApiParam(value = "Activation code")
                            @RequestParam String code,
                            @ApiParam(value = "language")
                            @RequestParam(defaultValue = "en") String language);
}
