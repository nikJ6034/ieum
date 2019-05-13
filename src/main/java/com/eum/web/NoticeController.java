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

import com.eum.notice.dto.NoticeSearchDTO;
import com.eum.notice.entity.Notice;
import com.eum.notice.service.NoticeService;

@RestController
@RequestMapping(value = "/api/web")
public class NoticeController {

	@Autowired
	NoticeService noticeService;

	@RequestMapping(value = "/notice", method = RequestMethod.GET)
	public Map<String, Object> list(NoticeSearchDTO noticeSearchDTO) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", noticeService.list(noticeSearchDTO));
		return map;
	}

	@RequestMapping(value = "/notice/{id}", method = RequestMethod.GET)
	public Map<String, Object> one(@PathVariable long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", noticeService.one(id));
		return map;
	}

	@RequestMapping(value = "/notice", method = RequestMethod.POST, consumes = "multipart/form-data")
	public Map<String, Object> insert(@RequestPart Notice notice, MultipartHttpServletRequest mtfRequest) throws IOException, Exception {
		
		List<MultipartFile> files = mtfRequest.getFiles("files");
		return noticeService.save(notice,files);
	}
	
	@RequestMapping(value="/notice/{id}", method=RequestMethod.POST, consumes = {"multipart/mixed","multipart/form-data"})
	public Map<String, Object> modify(@RequestPart Notice notice, MultipartHttpServletRequest mtfRequest){

		List<MultipartFile> files = mtfRequest.getFiles("files");
		return noticeService.modify(notice, files);
	}
	
	@RequestMapping(value="/notice/{id}", method=RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable long id){
		return noticeService.delete(id);
	}
	
	@RequestMapping(value = "/notice/file/{noticeId}/{fileId}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteFile(@PathVariable Long noticeId, @PathVariable Long fileId){
		return  noticeService.authFileDelete(noticeId,fileId);
    }
}
