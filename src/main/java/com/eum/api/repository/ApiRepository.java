package com.eum.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eum.api.entity.Api;

public interface ApiRepository extends JpaRepository<Api, Long>{
	public Optional<Api> findByUrl(String url);
}
