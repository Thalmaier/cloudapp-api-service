package info.thale.apiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import reactor.core.publisher.Hooks;

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
public class ApiServiceApplication {

	public static void main(String[] args) {
		var app = new SpringApplication(ApiServiceApplication.class);
		app.setWebApplicationType(WebApplicationType.REACTIVE);
		SpringApplication.run(ApiServiceApplication.class, args);
	}

}
