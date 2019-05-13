package com.eum.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eum.menu.entity.MenuRole;
import com.eum.menu.entity.MenuRoleKey;

public interface MenuRoleRepository extends JpaRepository<MenuRole, MenuRoleKey>{

}
