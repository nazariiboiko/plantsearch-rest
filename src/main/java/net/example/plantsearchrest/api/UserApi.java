package net.example.plantsearchrest.api;

import io.swagger.annotations.*;
import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.exception.NotFoundException;
import net.example.plantsearchrest.model.SinglePage;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "User management api (Only for admins)")
@RequestMapping("api/v1/users")
public interface UserApi {

    @ApiOperation("Get all users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    SinglePage<UserDto> getAllUsers(
            @ApiParam(value = "Number of page")
            @RequestParam(value = "page", defaultValue = "0") int page,
            @ApiParam(value = "Size of page")
            @RequestParam(value = "size", defaultValue = "20") int size);

    @ApiOperation("Get user by username")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @GetMapping("/username/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    UserDto getUserByUsername(
            @ApiParam(value = "User username", required = true) @PathVariable("username") String username);

    @ApiOperation("Get user by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @GetMapping("/id/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    UserDto getUserById(
            @ApiParam(value = "User username", required = true)
            @PathVariable("id") Long id) throws NotFoundException;

    @ApiOperation("Update user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Void> updateUser(
            @ApiParam(value = "User DTO") @RequestBody UserDto userDto);

    @ApiOperation("Set user status")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @PostMapping("/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Void> setStatus(
            @ApiParam(value = "User ID", required = true) @RequestParam int id,
            @ApiParam(value = "Status", required = true) @RequestParam String status);
}
