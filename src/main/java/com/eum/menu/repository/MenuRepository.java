package com.eum.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eum.menu.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long>{
	public Menu findByUrl(String url);
}
