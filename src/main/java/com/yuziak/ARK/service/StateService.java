package com.yuziak.ARK.service;

import java.util.List;

import com.yuziak.ARK.entity.State;

public interface StateService {

	void add(State state);

	void update(State state);

	List<State> getAll();

	List<State> findByContainerId(Long Id);

	State findById(Long id);

	void delete(Long id);
}
