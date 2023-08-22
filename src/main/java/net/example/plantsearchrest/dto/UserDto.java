package net.example.plantsearchrest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.example.plantsearchrest.entity.Role;
import net.example.plantsearchrest.entity.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDto implements Comparable<UserDto> {
    private Long id;
    private String email;
    private String login;
    private String password;
    private Role role;
    private Status status;
    private LocalDate registrationDate;
    private LocalDateTime lastLogin;

    @Override
    public int compareTo(UserDto other) {
        return this.getId().compareTo(other.getId());
    }
}
