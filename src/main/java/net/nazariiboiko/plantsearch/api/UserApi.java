package net.nazariiboiko.plantsearch.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.nazariiboiko.plantsearch.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Api [Admin Only]", description = "User API management")
@RequestMapping("/api/v1/users")
public interface UserApi {

    @Operation(summary = "Return list of users.")
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size
    );
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Block user by ID.")
    @PostMapping("/block/{id}")

    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> changeStatus(@RequestParam(required = true) Long id);
}
