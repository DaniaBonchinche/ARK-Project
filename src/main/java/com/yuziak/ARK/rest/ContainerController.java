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

import com.yuziak.ARK.entity.Container;
import com.yuziak.ARK.service.ClientService;
import com.yuziak.ARK.service.ContainerService;

import net.minidev.json.JSONObject;

@Controller
@RequestMapping("api/containers")
public class ContainerController {

	@Autowired
	private ContainerService containerService;

	@Autowired
	private ClientService clientService;

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
	public ResponseEntity<Container> getContainer(@PathVariable("id") Long containertid) {
		if (containertid == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Container response = containerService.findById(containertid);
		if (response != null) {
			return new ResponseEntity<Container>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Container>> getAllContainers() {
		List<Container> containers = containerService.getAll();
		if (containers == null) {
			return new ResponseEntity<List<Container>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Container>>(containers, HttpStatus.OK);
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "cl/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Container>> getContainersByClientId(@PathVariable("id") Long clientid) {
		List<Container> containers = containerService.findByClientId(clientid);
		if (containers == null) {
			return new ResponseEntity<List<Container>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Container>>(containers, HttpStatus.OK);
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Container> dellContainer(@PathVariable("id") Long containerid) {
		Container container = containerService.findById(containerid);
		containerService.delete(containerid);
		return new ResponseEntity<Container>(container, HttpStatus.OK);
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Container> addContainer(@RequestBody @Valid JSONObject json) {
		Container newContainer = new Container();
		newContainer.setAdress((String) json.get("adress"));
		newContainer.setClient(clientService.findById((Long) json.get("clientid")));
		containerService.add(newContainer);
		return new ResponseEntity<Container>(newContainer, HttpStatus.OK);
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Container> updateContainer(@RequestBody @Valid JSONObject json) {
		Container newContainer = new Container();
		newContainer.setId((Long) json.get("id"));
		newContainer.setAdress((String) json.get("adress"));
		newContainer.setClient(clientService.findById((Long) json.get("clientid")));
		newContainer.setStates(containerService.findById((Long) json.get("id")).getStates());
		containerService.update(newContainer);
		return new ResponseEntity<Container>(newContainer, HttpStatus.OK);
	}

}
