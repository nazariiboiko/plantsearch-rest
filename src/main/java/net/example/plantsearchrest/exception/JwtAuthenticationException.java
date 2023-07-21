package net.example.plantsearchrest.exception;

import javax.naming.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    private final String messageCode;
    public JwtAuthenticationException(String explanation, String messageCode) {
        super(explanation);
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return messageCode;
    }
}
