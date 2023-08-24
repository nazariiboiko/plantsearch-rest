package net.example.plantsearchrest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class AuthResponse {
    @JsonProperty("access_token")
    private String jwtToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
