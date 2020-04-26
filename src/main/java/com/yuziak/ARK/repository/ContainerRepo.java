package com.yuziak.ARK.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuziak.ARK.entity.Container;

public interface ContainerRepo extends PagingAndSortingRepository<Container, Long>{
	Container findByid(Long id);
}
