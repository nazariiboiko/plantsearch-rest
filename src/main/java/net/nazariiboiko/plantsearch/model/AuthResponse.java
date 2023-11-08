package net.nazariiboiko.plantsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
