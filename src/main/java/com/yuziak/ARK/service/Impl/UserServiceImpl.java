package com.yuziak.ARK.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.yuziak.ARK.entity.Role;
import com.yuziak.ARK.entity.User;
import com.yuziak.ARK.repository.RoleRepo;
import com.yuziak.ARK.repository.UserRepo;
import com.yuziak.ARK.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepository;

	@Autowired
	private RoleRepo roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User register(User user) {
		Role roleUser = roleRepository.findByname("ROLE_USER");
		List<Role> userRoles = new ArrayList<Role>();
		userRoles.add(roleUser);

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(userRoles);

		User registeredUser = userRepository.save(user);

		return registeredUser;
	}

	public List<User> getAll() {
		List<User> result = (List<User>) userRepository.findAll();
		return result;
	}

	public User findByUsername(String username) {
		User result = userRepository.findByusername(username);
		return result;
	}

	public User findById(Long id) {
		User result = userRepository.findById(id).orElse(null);

		if (result == null) {
			return null;
		}

		return result;
	}

	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User update(User user) {
		User tmp = userRepository.findByid(user.getId());
		tmp.setClient(user.getClient());
		tmp.setPassword(passwordEncoder.encode(user.getPassword()));
		tmp.setUsername(user.getUsername());
		userRepository.save(tmp);
		return user;
	}

	@Override
	public List<User> findByClientId(Long id) {
		List<User> result = (List<User>) userRepository.findAll();
		for (User user : result) {
			if (user.getClient().getId()!= id) {
				result.remove(user);
			}
		}
		
		return result;
	}
}
