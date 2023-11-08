package net.nazariiboiko.plantsearch.exception;

import org.hibernate.service.spi.ServiceException;

public class AlreadyExistsException extends ServiceException implements MessageCodeException {
    private final String code;

    public AlreadyExistsException(String s, String code) {
        super(s);
        this.code = code;
    }

    @Override
    public String getMessageCode() {
        return code;
    }
}

