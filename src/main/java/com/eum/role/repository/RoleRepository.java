package com.eum.role.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eum.role.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	public Optional<Role> findByRoleName(String RoleName);
}
