package com.yuziak.ARK.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuziak.ARK.entity.State;
import com.yuziak.ARK.repository.StateRepo;
import com.yuziak.ARK.service.StateService;

@Service
public class StateServiceImpl implements StateService {

	@Autowired
	StateRepo stateRepo;

	@Override
	public void add(State state) {
		stateRepo.save(state);
	}

	@Override
	public void update(State state) {
		State tmp = stateRepo.findByid(state.getId());
		tmp.setContainer(state.getContainer());
		tmp.setFilling(state.getFilling());
		tmp.setTemperature(state.getTemperature());
		tmp.setTime(state.getTime());
		stateRepo.save(tmp);
	}

	@Override
	public List<State> getAll() {
		List<State> result = (List<State>) stateRepo.findAll();
		return result;
	}

	@Override
	public List<State> findByContainerId(Long id) {
		List<State> result = (List<State>) stateRepo.findAll();
		List<State> resStates = new ArrayList<State>();
		for (State state : result) {
			if (state.getContainer() != null) {
				if (state.getContainer().getId() == id) {
					resStates.add(state);
				}
			}
		}
		return resStates;
	}

	@Override
	public State findById(Long id) {
		State result = stateRepo.findByid(id);
		return result;
	}

	@Override
	public void delete(Long id) {
		stateRepo.deleteById(id);
	}

}
