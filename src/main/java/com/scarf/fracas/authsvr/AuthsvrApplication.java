package com.scarf.fracas.authsvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AuthsvrApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthsvrApplication.class, args);
	}

}
