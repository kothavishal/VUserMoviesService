package com.vishal;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserMoviesApplication {

	final static Logger logger = Logger.getLogger(UserMoviesApplication.class);
	
	public static void main(String[] args) {

		SpringApplication.run(UserMoviesApplication.class, args);
		
		logger.info("User Movies Service running at http://localhost:8080/");
	}
}
