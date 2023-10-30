package tn.esprit.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "tn.esprit")
public class WebSemantiqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSemantiqueApplication.class, args);
	}

}
