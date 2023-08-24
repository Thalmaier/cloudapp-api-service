package info.thale.apiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.reactive.config.EnableWebFlux;

import reactor.core.publisher.Hooks;

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@EnableWebFlux
@EnableKafka
public class ApiServiceApplication {

	public static void main(String[] args) {
		var app = new SpringApplication(ApiServiceApplication.class);
		app.setWebApplicationType(WebApplicationType.REACTIVE);
		SpringApplication.run(ApiServiceApplication.class, args);
	}

}
