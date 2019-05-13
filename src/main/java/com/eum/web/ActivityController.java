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

import com.eum.activity.dto.ActivitySearchDTO;
import com.eum.activity.entity.Activity;
import com.eum.activity.service.ActivityService;

@RestController
@RequestMapping(value = "/api/web")
public class ActivityController {
	
	@Autowired
	ActivityService activityService;

	@RequestMapping(value = "/activity", method = RequestMethod.GET)
	public Map<String, Object> list(ActivitySearchDTO activitySearchDTO) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", activityService.list(activitySearchDTO));
		return map;
	}

	@RequestMapping(value = "/activity/{id}", method = RequestMethod.GET)
	public Map<String, Object> one(@PathVariable long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", activityService.one(id));
		return map;
	}

	@RequestMapping(value = "/activity", method = RequestMethod.POST, consumes = "multipart/form-data")
	public Map<String, Object> insert(@RequestPart Activity activity, MultipartHttpServletRequest mtfRequest) throws IOException, Exception {
		
		List<MultipartFile> files = mtfRequest.getFiles("files");
		return activityService.save(activity,files);
	}
	
	@RequestMapping(value="/activity/{id}", method=RequestMethod.POST, consumes = {"multipart/mixed","multipart/form-data"})
	public Map<String, Object> modify(@RequestPart Activity activity, MultipartHttpServletRequest mtfRequest){

		List<MultipartFile> files = mtfRequest.getFiles("files");
		return activityService.modify(activity, files);
	}
	
	@RequestMapping(value="/activity/{id}", method=RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable long id){
		return activityService.delete(id);
	}
	
	@RequestMapping(value = "/activity/image/{activityId}/{fileId}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteFile(@PathVariable Long activityId, @PathVariable Long fileId){
		return  activityService.authFileDelete(activityId,fileId);
    }

}
