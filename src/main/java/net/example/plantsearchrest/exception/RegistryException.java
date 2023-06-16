package net.example.plantsearchrest.exception;

import org.hibernate.service.spi.ServiceException;

public class RegistryException extends ServiceException {
    public RegistryException(String message) {
            super(message);
        }
}
