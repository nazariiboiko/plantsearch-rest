package net.example.plantsearchrest.api;

import io.swagger.annotations.*;
import net.example.plantsearchrest.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "User management api")
@RequestMapping("api/v1/users")
public interface UserApi {

    @ApiOperation("Get all users")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    List<UserDto>  getAllUsers();

    @ApiOperation("Get user by username")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User found"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @GetMapping("/username/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    UserDto getUserByUsername(
            @ApiParam(value = "User username", required = true) @PathVariable("username") String username);

    @ApiOperation("Get user by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User found"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @GetMapping("/id/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    UserDto getUserById(
            @ApiParam(value = "User username", required = true) @PathVariable("id") Long id);

    @ApiOperation("Update user")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Void> updateUser(
            @ApiParam(value = "User DTO") @RequestBody UserDto userDto);

    @ApiOperation("Set user status")
    @PostMapping("/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Void> setStatus(
            @ApiParam(value = "User ID", required = true) @RequestParam int id,
            @ApiParam(value = "Status", required = true) @RequestParam String status);
}
