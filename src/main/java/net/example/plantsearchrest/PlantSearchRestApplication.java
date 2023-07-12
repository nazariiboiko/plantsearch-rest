package net.example.plantsearchrest;

import net.example.plantsearchrest.config.PropertyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(PropertyConfig.class)
public class PlantSearchRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlantSearchRestApplication.class, args);
	}

}
