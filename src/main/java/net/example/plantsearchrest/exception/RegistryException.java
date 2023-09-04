package net.example.plantsearchrest.exception;

import org.hibernate.service.spi.ServiceException;

public class RegistryException extends ServiceException implements MessageCodeException {
    private final String messageCode;
    public RegistryException(String message, String messageCode) {
            super(message);
            this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }
}
