package com.eum.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eum.api.entity.ApiRole;
import com.eum.api.entity.ApiRoleKey;

public interface ApiRoleRepository extends JpaRepository<ApiRole, ApiRoleKey>{

}
