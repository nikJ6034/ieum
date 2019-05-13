package com.eum.menu.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eum.menu.entity.Menu;
import com.eum.menu.entity.MenuRole;
import com.eum.menu.entity.MenuRoleKey;
import com.eum.menu.repository.MenuRoleRepository;
import com.eum.role.entity.Role;
import com.eum.role.repository.RoleRepository;

@Service
@Transactional
public class MenuRoleService {
	
	@Autowired
	MenuRoleRepository menuRoleRepository;
	@Autowired
	RoleRepository roleRepository;
	
	public MenuRole getMenuRole(Collection<? extends GrantedAuthority> authorities, Menu menu) {
		MenuRole menuRole = new MenuRole();
		if(authorities == null) return menuRole;
		authorities.forEach(item -> {
			Optional<Role> role = roleRepository.findByRoleName(item.getAuthority());
			MenuRoleKey menuRoleKey = new MenuRoleKey();
			menuRoleKey.setMenu(menu);
			menuRoleKey.setRole(role.get());
			Optional<MenuRole> MemberMenuRole = menuRoleRepository.findById(menuRoleKey);
			
			if(MemberMenuRole.isPresent()) {
				MenuRole getMemberMenuRole = MemberMenuRole.get();
				if("Y".equals(getMemberMenuRole.getReadRole())) menuRole.setReadRole("Y");
				if("Y".equals(getMemberMenuRole.getWriteRole())) menuRole.setWriteRole("Y");
				if("Y".equals(getMemberMenuRole.getModifyRole())) menuRole.setModifyRole("Y");
				if("Y".equals(getMemberMenuRole.getDeleteRole())) menuRole.setDeleteRole("Y");
			}
		});
		
		return menuRole;
	}
}
