package pl.piomin.services.gatling;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ApiApplication.class).web(true).run(args);
	}
	
}
