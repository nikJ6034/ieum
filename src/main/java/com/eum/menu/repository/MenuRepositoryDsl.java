package com.eum.menu.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.eum.menu.dto.MenuDTO;
import com.eum.menu.entity.Menu;
import com.eum.menu.entity.QMenu;
import com.querydsl.jpa.JPQLQuery;

@Repository
public class MenuRepositoryDsl extends QuerydslRepositorySupport{

	@Autowired
	ModelMapper modelMapper;
	
	public MenuRepositoryDsl() {
		super(Menu.class);
	}
	
	public List<Menu> getMenuList(){
		QMenu menu1 = QMenu.menu;
		
		JPQLQuery<Menu> list = from(menu1);
		return list.where(menu1.level.eq(0),menu1.naviUseYN.eq("Y"),menu1.useYN.eq("Y")).fetch();
	}
	
	public Map<String, MenuDTO> getCurrentMenu(String url) {
		Map<String, MenuDTO> map = new HashMap<String, MenuDTO>();
		map.put("cMenu", null);
		map.put("pMenu", null);
		
		if(url != null && !url.equals("")) {
			QMenu menu = QMenu.menu;
			JPQLQuery<Menu> from = from(menu);
			Optional<Menu> cMenuOp = Optional.ofNullable(from.where(menu.naviUseYN.eq("Y"),menu.useYN.eq("Y"),menu.url.eq(url)).fetchOne());
			
			cMenuOp.ifPresent(m ->{
				MenuDTO cMenu = modelMapper.map(m, MenuDTO.class);
				map.put("cMenu", cMenu);
				
				MenuDTO pMenu = modelMapper.map(from(menu).where(menu.id.eq(cMenu.getPid())).fetchOne(),MenuDTO.class);
				map.put("pMenu", pMenu);
			});
		}
		
		return map;
	}
}
