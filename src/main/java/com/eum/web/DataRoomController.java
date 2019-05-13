package com.eum.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eum.dataRoom.dto.DataRoomSearchDTO;
import com.eum.dataRoom.entity.DataRoom;
import com.eum.dataRoom.service.DataRoomService;

@RestController
@RequestMapping(value = "/api/web")
public class DataRoomController {

	@Autowired
	DataRoomService dataRoomService;

	@RequestMapping(value = "/dataRoom", method = RequestMethod.GET)
	public Map<String, Object> list(DataRoomSearchDTO dataRoomSearchDTO) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", dataRoomService.list(dataRoomSearchDTO));
		return map;
	}

	@RequestMapping(value = "/dataRoom/{id}", method = RequestMethod.GET)
	public Map<String, Object> one(@PathVariable long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", dataRoomService.one(id));
		return map;
	}

	@RequestMapping(value = "/dataRoom", method = RequestMethod.POST, consumes = "multipart/form-data")
	public Map<String, Object> insert(@RequestPart DataRoom dataRoom, MultipartHttpServletRequest mtfRequest) throws IOException, Exception {
		List<MultipartFile> files = mtfRequest.getFiles("files");
		return dataRoomService.save(dataRoom,files);
	}
	
	@RequestMapping(value="/dataRoom/{id}", method=RequestMethod.POST, consumes = {"multipart/mixed","multipart/form-data"})
	public Map<String, Object> modify(@RequestPart DataRoom dataRoom, MultipartHttpServletRequest mtfRequest){
		List<MultipartFile> files = mtfRequest.getFiles("files");
		return dataRoomService.modify(dataRoom, files);
	}
	
	@RequestMapping(value="/dataRoom/{id}", method=RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable long id){
		return dataRoomService.delete(id);
	}
	
	@RequestMapping(value = "/dataRoom/file/{dataRoomId}/{fileId}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteFile(@PathVariable Long dataRoomId, @PathVariable Long fileId){
		return  dataRoomService.authFileDelete(dataRoomId,fileId);
    }
}
