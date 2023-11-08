package net.nazariiboiko.plantsearch.exception;

import net.nazariiboiko.plantsearch.exception.MessageCodeException;
import org.hibernate.service.spi.ServiceException;

public class UserAlreadyExistsException extends ServiceException implements MessageCodeException {

    private final String code;

    public UserAlreadyExistsException(String s, String code) {
        super(s);
        this.code = code;
    }

    @Override
    public String getMessageCode() {
        return null;
    }
}
