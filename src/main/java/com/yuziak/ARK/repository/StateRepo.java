package com.yuziak.ARK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuziak.ARK.entity.State;

public interface StateRepo extends JpaRepository<State, Long> {
	State findByid(Long id);
}
