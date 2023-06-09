package com.teamddd.duckmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class DuckmapApplication {

	public static void main(String[] args) {
		SpringApplication.run(DuckmapApplication.class, args
		);
	}

}
