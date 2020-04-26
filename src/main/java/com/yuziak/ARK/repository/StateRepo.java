package com.yuziak.ARK.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuziak.ARK.entity.State;

public interface StateRepo extends PagingAndSortingRepository<State, Long> {
	State findByid(Long id);
}
