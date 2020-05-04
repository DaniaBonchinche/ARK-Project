package com.yuziak.ARK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuziak.ARK.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
	Role findByname(String name);
}
