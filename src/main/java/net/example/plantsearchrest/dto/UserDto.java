package net.example.plantsearchrest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.example.plantsearchrest.entity.Role;
import net.example.plantsearchrest.entity.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDto {
    private Long id;
    private String email;
    private String login;
    private String password;
    private Role role;
    private Status status;
}
