package net.nazariiboiko.plantsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AuthRequest {
    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;
}
