package com.yuziak.ARK.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuziak.ARK.entity.User;

public interface UserRepo extends PagingAndSortingRepository<User, Long> {
	User findByusername(String name);
	User findByid(Long id);
}
