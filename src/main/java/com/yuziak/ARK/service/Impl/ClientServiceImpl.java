package com.yuziak.ARK.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuziak.ARK.entity.Client;
import com.yuziak.ARK.repository.ClientRepo;
import com.yuziak.ARK.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepo clientRepo;

	@Override
	public void add(Client client) {
		clientRepo.save(client);
	}

	@Override
	public List<Client> getAll() {
		List<Client> result = (List<Client>) clientRepo.findAll();
		return result;
	}

	@Override
	public Client findById(Long id) {
		Client result = clientRepo.findByid(id);
		return result;
	}

	@Override
	public void delete(Long id) {
		clientRepo.deleteById(id);
	}

	@Override
	public void update(Client client) {
		Client tmp = findById(client.getId());
		tmp.setContainers(client.getContainers());
		tmp.setEmail(client.getEmail());
		tmp.setName(client.getName());
		tmp.setPhoneNumber(client.getPhoneNumber());
		tmp.setUsers(client.getUsers());
		clientRepo.save(tmp);
	}

}
