package com.yuziak.ARK.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yuziak.ARK.entity.Client;
import com.yuziak.ARK.entity.User;
import com.yuziak.ARK.service.ClientService;
import com.yuziak.ARK.service.UserService;

import net.minidev.json.JSONObject;

@Controller
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	ClientService clientService;

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") Long userid) {
		if (userid == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User response = userService.findById(userid);
		if (response != null) {
			return new ResponseEntity<User>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAll();
		if (users == null) {
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "cl/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<User>> getUsersByClientId(@PathVariable("id") Long clientid) {
		List<User> users = userService.findByClientId(clientid);
		if (users == null) {
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> saveUser(@RequestBody @Valid JSONObject json) {
		User newUser = new User();
		newUser.setPassword((String) json.get("password"));
		newUser.setUsername((String) json.get("username"));
		Client tmpClient = clientService.findById((Long) json.get("client"));
		newUser.setClient(tmpClient);
		userService.register(newUser);
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> updateUser(@RequestBody @Valid JSONObject json) {
		User newUser = new User();
		newUser.setId((Long) json.get("id"));
		newUser.setPassword((String) json.get("password"));
		newUser.setUsername((String) json.get("username"));
		Client tmpClient = clientService.findById((Long) json.get("client"));
		newUser.setClient(tmpClient);
		userService.update(newUser);
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> dellUser(@PathVariable("id") Long userid) {
		User user = userService.findById(userid);
		userService.delete(userid);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}
