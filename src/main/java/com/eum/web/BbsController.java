package com.eum.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eum.bbs.repository.BbsRepository;

@RestController
@RequestMapping(value="/api/web")
public class BbsController{
	
	@Autowired
	BbsRepository bbsRepository;
	
	@RequestMapping(value="/bbs")
	public Map<String, Object> baseList(){
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		return result;
	}
}
