package com.yuziak.ARK.security.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuziak.ARK.entity.Client;

public class JwtUser implements UserDetails {
	
	
	private final Long id;
    private final String username;
    private final String password;
    private final boolean enabled;
    private final Client client;
    private final Collection<? extends GrantedAuthority> authorities;
    
    public JwtUser(
            Long id,
            String username,
            String password,
            Client client,
            Collection<? extends GrantedAuthority> authorities,
            boolean enabled
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.client=client;
        this.enabled = enabled;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @JsonIgnore
    public Client getClient() {
		return client;
	}

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

	public boolean isEnabled() {
		return enabled;
	}




}
