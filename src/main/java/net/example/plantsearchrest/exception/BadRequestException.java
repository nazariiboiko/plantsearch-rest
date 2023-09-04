package net.example.plantsearchrest.exception;

public class BadRequestException extends IllegalStateException implements MessageCodeException {
    private String messageCode;

    public BadRequestException(String explanation, String messageCode) {
        super(explanation);
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }
}
