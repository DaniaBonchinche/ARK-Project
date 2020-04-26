package com.yuziak.ARK.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yuziak.ARK.entity.User;
import com.yuziak.ARK.security.jwt.JwtUser;
import com.yuziak.ARK.security.jwt.JwtUserFactory;
import com.yuziak.ARK.service.UserService;


@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }
}
