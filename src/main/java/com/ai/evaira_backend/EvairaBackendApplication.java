package com.ai.evaira_backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class EvairaBackendApplication {

	public static void main(String[] args) {
		log.info("Hii");
		SpringApplication.run(EvairaBackendApplication.class, args);
	}

}
