package com.eum.activity.service;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eum.activity.dto.ActivityDTO;
import com.eum.activity.dto.ActivitySearchDTO;
import com.eum.activity.entity.Activity;
import com.eum.activity.repository.ActivityRepository;
import com.eum.activity.repository.ActivityRepositoryDsl;
import com.eum.attachFile.entity.AttachFile;
import com.eum.attachFile.repository.AttachFileRepository;
import com.eum.auth.dto.AuthDTO;
import com.eum.bbs.entity.Bbs;
import com.eum.config.oAuth2.AuthService;
import com.eum.util.file.image.UploadResourceImageFileUtil;

@Service
@Transactional
public class ActivityService {
	
	@Autowired
	ActivityRepository activityRepository;
	@Autowired
	ActivityRepositoryDsl activityRepositoryDsl;
	@Autowired
	ModelMapper modelMapper;
	@PersistenceContext
	protected EntityManager entityManager;
	@Autowired
	UploadResourceImageFileUtil uploadResourceImageFileUtil;
	@Autowired
    AuthService authService;
	@Autowired
	AttachFileRepository attachFileRepository;
	
	public Page<ActivityDTO> list(ActivitySearchDTO activitySearchDTO) {

		return activityRepositoryDsl
				.activityList(activitySearchDTO, PageRequest.of(activitySearchDTO.getCurrentPage()-1, activitySearchDTO.getLimit() /*, Sort.by("id")*/ ))
				.map(activity -> modelMapper.map(activity, ActivityDTO.class));
	}

	public ActivityDTO one(Long id) {
		return activityRepository.findById(id).map(activity -> modelMapper.map(activity, ActivityDTO.class)).get();
	}

	public Map<String, Object> save(Activity activity, List<MultipartFile> files) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			if(authService.getAuth().isAdmin()) {
				activity.setMember(authService.getAuth().getMember());
				Activity save = activityRepository.save(activity);
				List<AttachFile> attachFiles = files.stream().map(mf -> {
					AttachFile attachFile = null;
					try {
						attachFile = uploadResourceImageFileUtil.imageUploadWithThumbnail(mf.getOriginalFilename(), mf.getBytes());
						System.out.println(attachFile);
						entityManager.persist(attachFile);
						return attachFile;
					} catch (Exception e) {
						e.printStackTrace();
						return attachFile;
					}
				}).collect(Collectors.toList());

				save.setAttachImage(attachFiles);
				map.put("id", activity.getId());
				map.put("msg", "저장 되었습니다.");
				map.put("result", "success");
			}else {
				map.put("msg", "권한이 없습니다. 관리자가 아닙니다.");
				map.put("result", "fail");
			}

		} catch (Exception e) {
			map.put("result", "fail");
			map.put("msg", "exception");
			return 	map;
		}
		
		return map;
	}

	public Map<String, Object> modify(Activity activity, List<MultipartFile> files) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(authService.getAuth().isAdmin()) {
				Optional<Activity> findActivity = activityRepository.findById(activity.getId());
				findActivity.ifPresent(ac->{
					ac.setContent(activity.getContent());
					ac.setTitle(activity.getTitle());
					ac.setUpdateDate(new Date());
				});

				files.stream().forEach(mf -> {
					try {
						AttachFile attachFile = uploadResourceImageFileUtil.imageUploadWithThumbnail(mf.getOriginalFilename(), mf.getBytes());
						entityManager.persist(attachFile);
						findActivity.get().getAttachImage().add(attachFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				map.put("msg", "저장 되었습니다.");
				map.put("result", "success");
			}else {
				map.put("msg", "권한이 없습니다. 관리자가 아닙니다.");
				map.put("result", "fail");
			}
			
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("msg", "exception");
			return 	map;
		}

		return map;
	}

	public Map<String, Object> delete(long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Optional<Activity> activity = activityRepository.findById(id);
		try {
			if(authService.getAuth().isAdmin()) {
				activity.ifPresent(ac -> {
					ac.setDeleteYN("Y");
				});
				map.put("msg", "삭제 되었습니다.");
				map.put("result", "success");
			}else {
				map.put("msg", "권한이 없습니다. 관리자가 아닙니다.");
				map.put("result", "fail");
			}
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("msg", "exception");
			return 	map;
		}
		
		return map;
	}

	
	public Map<String, Object> authFileDelete(Long activityId, Long fileId) {
    	
    	AuthDTO auth = authService.getAuth();
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	Optional<Activity> activity = activityRepository.findById(activityId);
    	Optional<AttachFile> attachFile = attachFileRepository.findById(fileId);
    	
		if(auth.isAdmin()) {
			List<AttachFile> attachFiles = attachFile.map(af->{
	    		uploadResourceImageFileUtil.imageDelete(af.getFullPath(), af.getVirtualName());
	    		activity.get().getAttachImage().remove(af);
	    		return activity.get().getAttachImage();
	    	}).get();
			map.put("result", "success");
			map.put("msg", "삭제되었습니다.");
			map.put("attachFile", attachFiles);
		}else {
			map.put("result", "fail");
			map.put("msg", "관리자 외에는 자신의 게시물만 삭제할 수 있습니다.");
		}
		return map;
    	
    }

}
