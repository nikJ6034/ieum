package com.eum.banner.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eum.attachFile.entity.AttachFile;
import com.eum.attachFile.repository.AttachFileRepository;
import com.eum.auth.dto.AuthDTO;
import com.eum.banner.entity.Banner;
import com.eum.banner.repository.BannerRepository;
import com.eum.config.oAuth2.AuthService;
import com.eum.util.file.image.UploadResourceImageFileUtil;

@Service
@Transactional
public class BannerService {

	@Autowired
	BannerRepository bannerRepository;
	@Autowired
    AuthService authService;
	@Autowired
	UploadResourceImageFileUtil uploadResourceImageFileUtil;
	@PersistenceContext
	protected EntityManager entityManager;
	@Autowired
	AttachFileRepository attachFileRepository;
	
	public List<Banner> list(){
		return bannerRepository.findAll();
	}
	
	public Banner one(Long id) {
		return bannerRepository.findById(id).get();
	}
	
	public Map<String, Object> modify(Banner banner, List<MultipartFile> files) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(authService.getAuth().isAdmin()) {
				Banner save = bannerRepository.save(banner);
				files.stream().forEach(mf -> {
					AttachFile attachFile = null;
					try {
						attachFile = uploadResourceImageFileUtil.imageUpload(mf.getOriginalFilename(), mf.getBytes(), save.getWidth(), save.getHeight());
						entityManager.persist(attachFile);
						save.getAttachImage().add(attachFile);
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
	
public Map<String, Object> authFileDelete(Long activityId, Long fileId) {
    	
    	AuthDTO auth = authService.getAuth();
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	Optional<Banner> banner = bannerRepository.findById(activityId);
    	Optional<AttachFile> attachFile = attachFileRepository.findById(fileId);
    	
		if(auth.isAdmin()) {
			List<AttachFile> attachFiles = attachFile.map(af->{
	    		uploadResourceImageFileUtil.imageDelete(af.getFullPath(), af.getVirtualName());
	    		banner.get().getAttachImage().remove(af);
	    		return banner.get().getAttachImage();
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
