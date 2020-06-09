package com.example.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.AuthenticationRequest;
import com.example.model.AuthenticationResponse;
import com.example.util.JwtUtil;

@RestController
public class HelloResource {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService myUserDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@RequestMapping(path="/hello", method=RequestMethod.GET)
	public String hello() {
		return "Hello World";
	}
	
	@RequestMapping(path="/authenticate", method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		// Authenticating the user - Spring Security uses this for username password authentication.
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
					);
		} catch (BadCredentialsException ex) {
			throw new Exception("Incorrect username or password", ex);
		}
		
		// Creating JWT
		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
}
