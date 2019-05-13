package com.eum.web;

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

import com.eum.banner.entity.Banner;
import com.eum.banner.service.BannerService;

@RestController
@RequestMapping(value = "/api/web")
public class BannerController {
	
	@Autowired
	BannerService bannerService;
	
	@RequestMapping(value = "/banner", method = RequestMethod.GET)
	public Map<String, Object> list() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", bannerService.list());
		return map;
	}
	
	@RequestMapping(value = "/banner/{id}", method = RequestMethod.GET)
	public Map<String, Object> one(@PathVariable long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", bannerService.one(id));
		return map;
	}
	
	@RequestMapping(value="/banner/{id}", method=RequestMethod.POST, consumes = {"multipart/mixed","multipart/form-data"})
	public Map<String, Object> modify(@RequestPart Banner banner, MultipartHttpServletRequest mtfRequest){

		List<MultipartFile> files = mtfRequest.getFiles("files");
		return bannerService.modify(banner, files);
	}
	
	@RequestMapping(value = "/banner/image/{id}/{fileId}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteFile(@PathVariable Long id, @PathVariable Long fileId){
		return  bannerService.authFileDelete(id,fileId);
    }
}
