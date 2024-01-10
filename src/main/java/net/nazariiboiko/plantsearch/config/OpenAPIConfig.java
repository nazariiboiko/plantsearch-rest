package net.nazariiboiko.plantsearch.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class OpenAPIConfig implements WebMvcConfigurer {

    @Value("${nazariiboiko.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenApi() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment.");
        Contact contact = new Contact();
        contact.setEmail("nazarii.boiko2@gmail.com");
        contact.setName("Nazarii Boiko");
        contact.setUrl("null");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Word Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage plantsearch API.")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
