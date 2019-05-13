package com.eum.menu.service;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eum.menu.dto.MenuDTO;
import com.eum.menu.entity.Menu;
import com.eum.menu.repository.MenuRepository;
import com.eum.menu.repository.MenuRepositoryDsl;

@Service
public class MenuService {
	
	@Autowired
	MenuRepository menuRepository;
	@Autowired
	MenuRepositoryDsl menuRepositoryDsl;
	@Autowired
	ModelMapper modelMapper;
	
	public Menu getMenuByUrl(String url) {
		return menuRepository.findByUrl(url);
	}
	
	public List<Menu> getMenuList(){
		return menuRepositoryDsl.getMenuList();
	}
	
	public Map<String, MenuDTO> getCurrentMenu(String url) {
		return menuRepositoryDsl.getCurrentMenu(url);
//		if(menuRepositoryDsl.getParentsMenu(url) != null) {
//			MenuDTO map = modelMapper.map(menuRepositoryDsl.getCurrentMenu(url), MenuDTO.class);
//			return map;
//		}else {
//			return new MenuDTO();
//		}
		
	}
}
