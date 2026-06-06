package com.ai.evaira_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EvairaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvairaBackendApplication.class, args);
	}

}
