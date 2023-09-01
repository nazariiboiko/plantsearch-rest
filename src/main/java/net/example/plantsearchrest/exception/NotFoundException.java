package net.example.plantsearchrest.exception;

import javax.naming.NameNotFoundException;

public class NotFoundException extends NameNotFoundException {
    private final String messageCode;
    public NotFoundException(String explanation, String messageCode) {
        super(explanation);
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return messageCode;
    }
}
