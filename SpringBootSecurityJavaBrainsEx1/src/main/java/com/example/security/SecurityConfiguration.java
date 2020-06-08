package com.example.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	/* This configuration is for authentication. It can be in-memory authentication or 
	 * jdbc authentication or any other.*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Set your configuration on the auth object
		/* In-memory authentication.
		auth.inMemoryAuthentication()
			.withUser("user")
			.password("user")
			.roles("USER")
			.and()
			.withUser("admin")
			.password("admin")
			.roles("ADMIN");
			*/
		
		/* JDBC authentication. 
		 * In case of embedded DB like H2, it just requires this much configuration - 
		 * 		Get a bean of type DataSource by Autowiring. The bean is created 
		 * 		automatically in case of embedded DB. */
	/*	auth.jdbcAuthentication()
			.dataSource(dataSource)*/
			/* These instructions tell the Spring Security to populate the schema and 
			 * the data automatically. Even if the DB config is external like MySql, 
			 * then also these configurations are applied. */
/*			.withDefaultSchema() 
			.withUser(User.withUsername("user")
						.password("user")
						.roles("USER"))
			.withUser(User.withUsername("admin")
					.password("admin")
					.roles("ADMIN"));*/
		
		/* JDBC authentication. Without defaultschema method.
		 * In case of embedded DB like H2, it just requires this much configuration - 
		 * 		Get a bean of type DataSource by Autowiring. The bean is created 
		 * 		automatically in case of embedded DB. */
		auth.jdbcAuthentication()
			.dataSource(dataSource)
		/* This is by default, when we use the default schema. But, if we change the 
		 * default schema, then we can configure the DDL here. */
		/*	.usersByUsernameQuery("select username, password, enabled from users "
					+ "where username = ?")
			.authoritiesByUsernameQuery("select username, authority from authorities "
					+ "where username = ?")*/;
	}
	
	/* This configuration is for authorization. */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/admin").hasRole("ADMIN")
			.antMatchers("/user").hasAnyRole("USER","ADMIN")
			.antMatchers("/").permitAll()
			/* formlogin is optional here, as it is given by default. Instead we can 
			 * configure something else if we want to. */
			.and().formLogin(); 
	}
}
