package com.wpam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class BealockApplication {

	public static void main(String[] args) {
		SpringApplication.run(BealockApplication.class, args);
	}
}
