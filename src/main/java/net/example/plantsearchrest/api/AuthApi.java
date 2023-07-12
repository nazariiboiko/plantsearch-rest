package net.example.plantsearchrest.api;

import io.swagger.annotations.*;
import net.example.plantsearchrest.dto.AuthRequestDto;
import net.example.plantsearchrest.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
                         @RequestBody AuthRequestDto requestDto);

    @ApiOperation("Sign up")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/register")
    ResponseEntity register(@ApiParam(value = "User DTO")
                            @RequestBody UserDto userDto);

}
