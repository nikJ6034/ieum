package com.eum.banner.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.eum.attachFile.entity.AttachFile;
import com.eum.attachFile.repository.AttachFileRepository;
import com.eum.auth.dto.AuthDTO;
import com.eum.banner.entity.Banner;
import com.eum.banner.entity.BannerImage;
import com.eum.banner.entity.BannerImageId;
import com.eum.banner.repository.BannerImageRepository;
import com.eum.banner.repository.BannerRepository;
import com.eum.config.oAuth2.AuthService;
import com.eum.util.file.image.UploadResourceImageFileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	@Autowired
	BannerImageRepository bannerImageRepository;
	
	public List<Banner> list(){
		return bannerRepository.findAll();
	}
	
	public Banner one(Long id) {
		return bannerRepository.findById(id).get();
	}
	
	public Map<String, Object> modify(Banner banner, List<MultipartFile> files, List<BannerImage> bannerImage) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(authService.getAuth().isAdmin()) {
				banner.getBannerImage().stream().forEach(bi->{
					bannerImageRepository.save(bi);
				});
				Banner save = bannerRepository.save(banner);

				for(int i = 0; i < files.size(); i++){
					MultipartFile mf = files.get(i);
					BannerImage bi = bannerImage.get(i);
					AttachFile attachFile = uploadResourceImageFileUtil.imageUpload(mf.getOriginalFilename(), mf.getBytes(), save.getHeight());
					entityManager.persist(attachFile);
					bi.setBanner(save);
					bi.setAttachFile(attachFile);
					bannerImageRepository.save(bi);
				}
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
	
public Map<String, Object> authFileDelete(Long bannerId, Long fileId) {
    	
    	AuthDTO auth = authService.getAuth();
    	HashMap<String, Object> map = new HashMap<String, Object>();

    	Optional<AttachFile> attachFile = attachFileRepository.findById(fileId);
    	
		if(auth.isAdmin()) {
			/*List<BannerImage> attachFiles = attachFile.map(af->{
	    		uploadResourceImageFileUtil.imageDelete(af.getFullPath(), af.getVirtualName()); //이미지 파일을 삭제한다.
				BannerImage bannerImage = new BannerImage();
				bannerImage.setAttachFile(af);
				bannerImage.setBanner(banner.get());
				boolean contains = banner.get().getBannerImage().contains(bannerImage);
				System.out.println(contains);
				return banner.get().getBannerImage();
	    	}).get();*/
			BannerImageId bannerImageId = new BannerImageId();
			bannerImageId.setBanner(bannerId);
			bannerImageId.setAttachFile(fileId);
			uploadResourceImageFileUtil.imageDelete(attachFile.get().getFullPath(), attachFile.get().getVirtualName()); //이미지 파일을 삭제한다.
			bannerImageRepository.deleteById(bannerImageId);
			attachFileRepository.deleteById(fileId);
			Optional<Banner> banner = bannerRepository.findById(bannerId);
			map.put("result", "success");
			map.put("msg", "삭제되었습니다.");
			map.put("attachFile", banner.get().getBannerImage());
		}else {
			map.put("result", "fail");
			map.put("msg", "관리자 외에는 자신의 게시물만 삭제할 수 있습니다.");
		}
		return map;
    	
    }
}
