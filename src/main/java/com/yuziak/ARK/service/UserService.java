package com.yuziak.ARK.service;

import java.util.List;

import com.yuziak.ARK.entity.User;

public interface UserService {

	User register(User user);

	User update(User user);

	List<User> getAll();
	
	List<User>  findByClientId(Long id);

	User findByUsername(String username);

	User findById(Long id);

	void delete(Long id);
}
