package net.example.plantsearchrest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class WebMvcConfig {
    @Bean
    public LocaleResolver localeResolver() {
        return new CustomLocaleResolver();
    }

}
