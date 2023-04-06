package com.capstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.capstone")
@EntityScan(basePackages = "com.capstone.entity")
@EnableJpaRepositories(basePackages = "com.capstone.repository")
public class InventoryProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryProjectApplication.class, args);
	}

}
