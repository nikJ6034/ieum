package com.eum.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eum.menu.entity.Menu;
import com.eum.menu.service.MenuService;

@RestController
public class MenuController {
	
	@Autowired
	MenuService menuService;
	
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public List<Menu> list() {
		return menuService.getMenuList();
	}
	
	
}
