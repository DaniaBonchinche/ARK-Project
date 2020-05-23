package com.yuziak.ARK.rest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.yuziak.ARK.entity.Container;
import com.yuziak.ARK.entity.State;
import com.yuziak.ARK.service.ClientService;
import com.yuziak.ARK.service.ContainerService;
import com.yuziak.ARK.service.StateService;

import javassist.expr.NewArray;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping("api/containers")
public class ContainerController {

	@Autowired
	private ContainerService containerService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private StateService stateService;

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
	@RequestMapping(value = "time/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
	public ResponseEntity<JSONObject> getTime(@PathVariable("id") Long containertid) {
		JSONObject result = new JSONObject();
		List<State> allStatesList = stateService.findByContainerId(containertid);
		if (allStatesList.size() < 5) {
			result.appendField("ok", false);
			return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
		}
		result.appendField("ok", true);
		State[] allStatesArr = new State[allStatesList.size()];
		int index = 0;
		for (State state : allStatesList) {
			allStatesArr[index] = state;
			index++;
		}
		Arrays.sort(allStatesArr, new Comparator<State>() {
			@Override
			public int compare(State o1, State o2) {
				return o1.getTime().compareTo(o2.getTime());
			}
		});
		int i1 = 0, i2 = 0, i3 = 0, i4 = 0;
		int[] times = { 0, 0, 0, 0 };
		int temp = 0;
		for (int i = 1; i < allStatesArr.length - 1; i++) {
			temp = allStatesArr[i + 1].getFilling() - allStatesArr[i].getFilling();
			switch (i % 4) {
			case 0:
				times[0] += temp > 0 ? temp : 100 - temp;
				i1++;
				break;
			case 1:
				times[1] += temp > 0 ? temp : 100 - temp;
				i2++;
				break;
			case 2:
				times[2] += temp > 0 ? temp : 100 - temp;
				i3++;
				break;
			case 3:
				times[3] += temp > 0 ? temp : 100 - temp;
				i4++;
				break;
			}
		}
		times[0] = times[0] / i1;
		times[1] = times[1] / i2;
		times[2] = times[2] / i3;
		times[3] = times[3] / i4;
		State last = allStatesArr[allStatesArr.length - 1];
		result.appendField("temperature", last.getTemperature());
		result.appendField("filling", last.getFilling());
		result.appendField("time", last.getTime());
		int tempTime = (allStatesArr.length - 1) % 4;
		if (last.getFilling() + times[tempTime] > 100) {
			result.appendField("timeout", new Date(last.getTime().getTime() + 21600 * 1000));
			return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
		} else if (last.getFilling() + times[tempTime] + times[(tempTime + 1) % 4] > 100) {
			result.appendField("timeout", new Date(last.getTime().getTime() + 21600 * 2000));
			return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
		} else if (last.getFilling() + times[tempTime] + times[(tempTime + 1) % 4] + times[(tempTime + 2) % 4] > 100) {
			result.appendField("timeout", new Date(last.getTime().getTime() + 21600 * 3000));
			return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
		} else {
			result.appendField("timeout", new Date(last.getTime().getTime() + 21600 * 4000));
			return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
		}

	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<JSONObject>> getAllContainers() {
		List<Container> containers = containerService.getAll();
		if (containers == null) {
			return new ResponseEntity<List<JSONObject>>(HttpStatus.NOT_FOUND);
		}
		List<JSONObject> result = new LinkedList<JSONObject>();
		for (Container cont : containers) {
			if (cont.getClient() == null) {
				result.add(
						new JSONObject().appendField("id", cont.getId()).appendField("longitude", cont.getLongitude())
								.appendField("latitude", cont.getLatitude()).appendField("adress", cont.getAdress()));
			} else {
				result.add(new JSONObject().appendField("id", cont.getId())
						.appendField("longitude", cont.getLongitude()).appendField("latitude", cont.getLatitude())
						.appendField("adress", cont.getAdress()).appendField("client", cont.getClient().getId()));
			}
		}
		System.out.println("OK");
		return new ResponseEntity<List<JSONObject>>(result, HttpStatus.OK);
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "cl/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<JSONObject>> getContainersByClientId(@PathVariable("id") Long clientid) {
		List<Container> containers = containerService.findByClientId(clientid);
		if (containers == null) {
			return new ResponseEntity<List<JSONObject>>(HttpStatus.NOT_FOUND);
		}
		List<JSONObject> result = new LinkedList<JSONObject>();
		for (Container cont : containers) {
			result.add(new JSONObject().appendField("id", cont.getId()).appendField("longitude", cont.getLongitude())
					.appendField("latitude", cont.getLatitude()).appendField("adress", cont.getAdress())
					.appendField("client", cont.getClient().getId()));
		}
		return new ResponseEntity<List<JSONObject>>(result, HttpStatus.OK);
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
		System.out.println("sjkhsd");
		Container newContainer = new Container();
		newContainer.setAdress((String) json.get("adress"));
		newContainer.setLatitude((Double) json.get("latitude"));
		newContainer.setLongitude((Double) json.get("longitude"));
		newContainer.setClient(clientService.findById(toLong((Integer) json.get("clientid"))));
		containerService.add(newContainer);
		return new ResponseEntity<Container>(newContainer, HttpStatus.OK);
	}

	@CrossOrigin(origins = { "http://localhost:3000", "http://109.86.204.249:3000" })
	@RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Container> updateContainer(@RequestBody @Valid JSONObject json) {
		Container newContainer = new Container();
		newContainer.setId(toLong((Integer) json.get("id")));
		newContainer.setAdress((String) json.get("adress"));
		newContainer.setLatitude((Double) json.get("latitude"));
		newContainer.setLongitude((Double) json.get("longitude"));
		newContainer.setClient(clientService.findById(toLong((Integer) json.get("clientid"))));
		newContainer.setStates(containerService.findById(toLong((Integer) json.get("id"))).getStates());
		containerService.update(newContainer);
		return new ResponseEntity<Container>(newContainer, HttpStatus.OK);
	}

	public Long toLong(Integer x) {
		Long longx = (long) 0 + x;
		return longx;
	}
	
	public double toDouble(Integer x) {
		double doublex = (double) 0 + x;
		return doublex;
	}

}
