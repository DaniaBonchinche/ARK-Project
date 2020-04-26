package com.yuziak.ARK.service;

import java.util.List;

import com.yuziak.ARK.entity.Client;

public interface ClientService {

	void add(Client client);
	
	void update(Client client);
	
	List<Client> getAll();
	
	Client findById(Long id);
	
	void delete(Long id);
}
