package com.example.cf.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class CfManagerApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(CfManagerApplication.class, args);
	}

}
