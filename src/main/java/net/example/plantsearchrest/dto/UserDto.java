package net.example.plantsearchrest.dto;

import lombok.Data;
import net.example.plantsearchrest.entity.Role;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String login;
    private String password;
    private Role role;

}
