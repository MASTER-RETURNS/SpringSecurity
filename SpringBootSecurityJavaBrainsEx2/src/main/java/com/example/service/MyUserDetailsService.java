package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.model.MyUserDetails;
import com.example.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		/* returning the instance of UserDetailsService. Hardcoding for now, will do with JPA
		 * further. */
		//return new MyUserDetails(userName);
		
		/* Fetching the user Using JPA */
		Optional<User> user = userRepo.findByUserName(userName);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Username " + userName + " not found"));
		UserDetails myUserDetails = new MyUserDetails(user.get());
		return myUserDetails;
	}

}
