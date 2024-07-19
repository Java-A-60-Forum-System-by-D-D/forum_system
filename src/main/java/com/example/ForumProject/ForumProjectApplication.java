package com.example.ForumProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })/*TODO this is just to remove the autoconfucation of spring security*/
public class ForumProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumProjectApplication.class, args);
	}

}
