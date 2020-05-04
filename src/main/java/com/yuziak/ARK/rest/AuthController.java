package com.yuziak.ARK.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuziak.ARK.dto.AuthenticationRequestDto;
import com.yuziak.ARK.dto.JwtDto;
import com.yuziak.ARK.entity.User;
import com.yuziak.ARK.security.jwt.JwtTokenProvider;
import com.yuziak.ARK.service.UserService;


@RestController
@RequestMapping(value = "/login")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserService userService;
	
	
	@CrossOrigin
	@PostMapping()
	public ResponseEntity login(@RequestBody AuthenticationRequestDto us) {
		try {
			String username = us.getUsername();
			UsernamePasswordAuthenticationToken tok =new UsernamePasswordAuthenticationToken(username, us.getPassword());
			authenticationManager.authenticate(tok);
			User user = userService.findByUsername(username);
			if (user == null) {
				throw new UsernameNotFoundException("User with username: " + username + " not found");
			}

			String token = jwtTokenProvider.createToken(username, user.getRoles());

			Map<Object, Object> response = new HashMap<>();
			response.put("username", username);
			response.put("token", token);
			response.put("id", user.getId());

			return ResponseEntity.ok(response);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username or password");
		}
	}
	
	@CrossOrigin
	@GetMapping
	public int test() {
		return 0;
	}

	@CrossOrigin
	@PostMapping("/ver")
	public ResponseEntity ver(@RequestBody JwtDto Jwt) {
		try {

			boolean res = jwtTokenProvider.verToken(Jwt.getToken());
			Map<Object, Object> response = new HashMap<>();
			response.put("flag", res);
			
			return ResponseEntity.ok(response);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("JWT token is expired or invalid");
		}
	}

}
