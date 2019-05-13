package com.eum.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eum.freeBoard.dto.FreeBoardSearchDTO;
import com.eum.freeBoard.entity.FreeBoard;
import com.eum.freeBoard.service.FreeBoardService;

@RestController
@RequestMapping(value="/api/web")
public class FreeBoardController{

	@Autowired
	FreeBoardService freeBoardService;
	
	@RequestMapping(value = "/freeBoard", method = RequestMethod.GET)
	public Map<String, Object> list(FreeBoardSearchDTO freeBoardSearchDTO) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", freeBoardService.list(freeBoardSearchDTO));
		return map;
	}

	@RequestMapping(value = "/freeBoard/{id}", method = RequestMethod.GET)
	public Map<String, Object> one(@PathVariable long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", freeBoardService.one(id));
		return map;
	}

	@RequestMapping(value = "/freeBoard", method = RequestMethod.POST)
	public Map<String, Object> insert(@RequestBody FreeBoard freeBoard) throws IOException, Exception {
		System.out.println(freeBoard);
		return freeBoardService.save(freeBoard);
	}
	
	@RequestMapping(value="/freeBoard/{id}", method=RequestMethod.PUT)
	public Map<String, Object> modify(@RequestBody FreeBoard freeBoard){
		return freeBoardService.modify(freeBoard);
	}
	
	@RequestMapping(value="/freeBoard/{id}", method=RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable long id){
		return freeBoardService.delete(id);
	}
}
