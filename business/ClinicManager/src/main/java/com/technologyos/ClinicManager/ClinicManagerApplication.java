package com.technologyos.ClinicManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.technologyos.*"})
public class ClinicManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicManagerApplication.class, args);
	}

}
