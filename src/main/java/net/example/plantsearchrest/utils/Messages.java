package net.example.plantsearchrest.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class Messages {
    private final MessageSource messageSource;

    public String getMessage(String errorCode, Locale locale) {
        return messageSource.getMessage(errorCode, null, locale);
    }
}
