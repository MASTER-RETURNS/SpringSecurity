package com.example.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	/* This configuration is for authentication. It can be in-memory authentication or 
	 * jdbc authentication or ldap or any other.*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Set your configuration on the auth object
		auth.ldapAuthentication()
			//user id to search
			.userDnPatterns("uid={0},ou=people")
			//organization unit to search
			.groupSearchBase("ou=groups")
			.contextSource()
			//url where the ldap instance is running. use the domain component given in application.properties file.
			.url("ldap://localhost:8389/dc=springframework,dc=org")
			.and().passwordCompare()
			//the password encoder
			.passwordEncoder(new BCryptPasswordEncoder())
			//the attribute where the password is stored.
			.passwordAttribute("userPassword");
	}
	
	/* This configuration is for authorization. */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.anyRequest().fullyAuthenticated()
			.and().formLogin(); 
	}
}

