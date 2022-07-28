package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AvtoprokatApplication {
	public static void main(String[] args) {
		SpringApplication.run(AvtoprokatApplication.class, args);
	}

}
