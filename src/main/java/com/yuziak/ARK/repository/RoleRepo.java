package com.yuziak.ARK.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuziak.ARK.entity.Role;

public interface RoleRepo extends PagingAndSortingRepository<Role, Long> {
	Role findByname(String name);
}
