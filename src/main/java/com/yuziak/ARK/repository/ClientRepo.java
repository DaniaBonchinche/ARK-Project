package com.yuziak.ARK.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuziak.ARK.entity.Client;

public interface ClientRepo extends PagingAndSortingRepository<Client, Long> {
	Client findByid(Long id);
}
