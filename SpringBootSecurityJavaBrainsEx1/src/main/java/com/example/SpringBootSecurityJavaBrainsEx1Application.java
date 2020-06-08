package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/* 
 * Spring Security with In-memory and JDBC authentication.
 */
@SpringBootApplication
public class SpringBootSecurityJavaBrainsEx1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJavaBrainsEx1Application.class, args);
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
