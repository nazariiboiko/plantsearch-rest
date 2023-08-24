package net.example.plantsearchrest.model;

import lombok.Data;

@Data
public class AuthRefreshRequest {
    String refreshToken;
}
