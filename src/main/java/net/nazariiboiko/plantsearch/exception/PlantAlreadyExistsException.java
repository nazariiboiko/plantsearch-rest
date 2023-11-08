package net.nazariiboiko.plantsearch.exception;

import org.hibernate.service.spi.ServiceException;

public class PlantAlreadyExistsException extends ServiceException implements MessageCodeException {
    private final String code;

    public PlantAlreadyExistsException(String s, String code) {
        super(s);
        this.code = code;
    }

    @Override
    public String getMessageCode() {
        return code;
    }
}
