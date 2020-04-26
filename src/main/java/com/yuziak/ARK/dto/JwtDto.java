package com.yuziak.ARK.dto;

import java.io.Serializable;

public class JwtDto implements Serializable {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}