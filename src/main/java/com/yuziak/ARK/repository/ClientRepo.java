package com.yuziak.ARK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuziak.ARK.entity.Client;

public interface ClientRepo extends JpaRepository<Client, Long> {
	Client findByid(Long id);
}
