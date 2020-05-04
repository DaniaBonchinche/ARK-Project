package com.yuziak.ARK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuziak.ARK.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
	User findByusername(String name);
	User findByid(Long id);
}
