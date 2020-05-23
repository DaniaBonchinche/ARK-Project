package com.yuziak.ARK.rest;

import java.util.Date;
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
import com.yuziak.ARK.entity.State;
import com.yuziak.ARK.service.ContainerService;
import com.yuziak.ARK.service.StateService;

import net.minidev.json.JSONObject;

@Controller
@RequestMapping("api/states")
public class StateController {

	@Autowired
	StateService stateService;

	@Autowired
	ContainerService containerService;

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
	public ResponseEntity<State> getState(@PathVariable("id") Long stateid) {
		if (stateid == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		State response = stateService.findById(stateid);
		if (response != null) {
			return new ResponseEntity<State>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<State>> getAllStates() {
		List<State> states = stateService.getAll();
		if (states == null) {
			return new ResponseEntity<List<State>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<State>>(states, HttpStatus.OK);
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "cont/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<State>> getStatesByContainer(@PathVariable("id") Long containerid) {
		List<State> states = stateService.findByContainerId(containerid);
		if (states == null) {
			return new ResponseEntity<List<State>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<State>>(states, HttpStatus.OK);
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<State> dellState(@PathVariable("id") Long stateid) {
		State state = stateService.findById(stateid);
		stateService.delete(stateid);
		return new ResponseEntity<State>(state, HttpStatus.OK);
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<State> addState(@RequestBody @Valid JSONObject json) {
		State newState = new State();
		newState.setFilling((Integer) json.get("filling"));
		newState.setTemperature((Integer) json.get("temperature"));
		if (json.get("time") == null) {
			newState.setTime(new Date());
		}
		newState.setContainer(containerService.findById(toLong((Integer) json.get("containerid"))));
		stateService.add(newState);
		return new ResponseEntity<State>(newState, HttpStatus.OK);
	}

	public Long toLong(Integer x) {
		Long longx = (long) 0 + x;
		return longx;
	}
}
