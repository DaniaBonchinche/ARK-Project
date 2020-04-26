package com.yuziak.ARK.service;

import java.util.List;

import com.yuziak.ARK.entity.Container;

public interface ContainerService {

	void add(Container container);

	void update(Container container);
	
	List<Container> getAll();
	
	List<Container> findByClientId(Long Id);

	Container findById(Long id);

	void delete(Long id);

}
