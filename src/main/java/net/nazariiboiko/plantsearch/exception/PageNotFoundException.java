package net.nazariiboiko.plantsearch.exception;

import org.hibernate.service.spi.ServiceException;

public class PageNotFoundException extends ServiceException implements MessageCodeException {
    private final String code;

    public PageNotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }

    @Override
    public String getMessageCode() {
        return null;
    }
}
