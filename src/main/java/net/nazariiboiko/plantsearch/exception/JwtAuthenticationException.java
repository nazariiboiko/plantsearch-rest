package net.nazariiboiko.plantsearch.exception;

import org.hibernate.service.spi.ServiceException;

public class JwtAuthenticationException extends ServiceException implements MessageCodeException {

    private final String code;
    public JwtAuthenticationException(String message, String code) {
        super(message);
        this.code = code;
    }

    @Override
    public String getMessageCode() {
        return code;
    }
}
