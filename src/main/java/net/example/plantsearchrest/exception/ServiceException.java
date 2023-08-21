package net.example.plantsearchrest.exception;

public class ServiceException extends org.hibernate.service.spi.ServiceException {
    private final String messageCode;
    public ServiceException(String message, String messageCode) {
        super(message);
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return messageCode;
    }
}
