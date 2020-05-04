package com.yuziak.ARK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuziak.ARK.entity.Container;

public interface ContainerRepo extends JpaRepository<Container, Long>{
	Container findByid(Long id);
}
