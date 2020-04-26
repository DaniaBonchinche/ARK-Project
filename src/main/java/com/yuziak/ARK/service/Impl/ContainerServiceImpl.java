package com.yuziak.ARK.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuziak.ARK.entity.Container;
import com.yuziak.ARK.repository.ContainerRepo;
import com.yuziak.ARK.service.ContainerService;

@Service
public class ContainerServiceImpl implements ContainerService {

	@Autowired
	ContainerRepo containerRepo;

	@Override
	public void add(Container container) {
		containerRepo.save(container);
	}

	@Override
	public List<Container> getAll() {
		List<Container> result = (List<Container>) containerRepo.findAll();
		return result;
	}

	@Override
	public List<Container> findByClientId(Long id) {
		List<Container> result = (List<Container>) containerRepo.findAll();
		for (Container container : result) {
			if (container.getClient().getId() != id) {
				result.remove(container);
			}
		}
		return result;
	}

	@Override
	public Container findById(Long id) {
		Container result = containerRepo.findByid(id);
		return result;
	}

	@Override
	public void delete(Long id) {
		containerRepo.deleteById(id);
	}

	@Override
	public void update(Container container) {
		Container tmp = containerRepo.findByid(container.getId());
		tmp.setAdress(container.getAdress());
		tmp.setClient(container.getClient());
		tmp.setStates(container.getStates());
		containerRepo.save(tmp);
	}

}
