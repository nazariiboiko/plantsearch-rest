package net.example.plantsearchrest.exception;

import javax.naming.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String explanation) {
        super(explanation);
    }
}
