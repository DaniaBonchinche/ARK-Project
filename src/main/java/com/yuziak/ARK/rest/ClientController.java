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
import com.yuziak.ARK.service.ClientService;

import net.minidev.json.JSONObject;

@Controller
@RequestMapping("api/clients")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
	public ResponseEntity<Client> getClient(@PathVariable("id") Long clientid) {
		if (clientid == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Client response = clientService.findById(clientid);
		if (response != null) {
			return new ResponseEntity<Client>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Client>> getAllClients() {
		List<Client> clients = clientService.getAll();
		if (clients == null) {
			return new ResponseEntity<List<Client>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Client> addClient(@RequestBody @Valid JSONObject json) {
		Client newClient = new Client();
		newClient.setName((String) json.get("name"));
		newClient.setEmail((String) json.get("email"));
		newClient.setPhoneNumber((String) json.get("phone"));
		clientService.add(newClient);
		return new ResponseEntity<Client>(newClient, HttpStatus.OK);
	}
	
	
	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Client> updateClient(@RequestBody @Valid JSONObject json) {
		Client newClient = new Client();
		newClient.setId((Long) json.get("id"));
		newClient.setName((String) json.get("name"));
		newClient.setEmail((String) json.get("email"));
		newClient.setPhoneNumber((String) json.get("phone"));
		newClient.setContainers(clientService.findById((Long) json.get("id")).getContainers());
		newClient.setUsers(clientService.findById((Long) json.get("id")).getUsers());
		clientService.update(newClient);
		return new ResponseEntity<Client>(newClient, HttpStatus.OK);
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Client> dellClient(@PathVariable("id") Long clientid) {
		Client client = clientService.findById(clientid);
		clientService.delete(clientid);
		return new ResponseEntity<Client>(client, HttpStatus.OK);
	}

}
