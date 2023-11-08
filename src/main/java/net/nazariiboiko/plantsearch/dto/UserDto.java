package net.nazariiboiko.plantsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.nazariiboiko.plantsearch.enums.Role;
import net.nazariiboiko.plantsearch.enums.Status;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    @Email(message = "Invalid email format")
    private String email;

    @JsonProperty("login")
    @NotBlank(message = "Login is required")
    @Size(min = 3, max = 50, message = "Login must be between 5 and 50 characters")
    private String login;


    @JsonProperty("password")
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    @JsonProperty("role")
    private Role role;

    @JsonProperty("status")
    private Status status;
}
