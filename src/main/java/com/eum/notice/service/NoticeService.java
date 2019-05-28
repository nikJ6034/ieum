package com.eum.notice.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.eum.attachFile.entity.AttachFile;
import com.eum.attachFile.repository.AttachFileRepository;
import com.eum.auth.dto.AuthDTO;
import com.eum.config.oAuth2.AuthService;
import com.eum.notice.dto.NoticeDTO;
import com.eum.notice.dto.NoticeSearchDTO;
import com.eum.notice.entity.Notice;
import com.eum.notice.repository.NoticeRepository;
import com.eum.notice.repository.NoticeRepositoryDsl;
import com.eum.util.file.UploadFileUtil;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class NoticeService {
	@Autowired
	NoticeRepository noticeRepository;
	@Autowired
	NoticeRepositoryDsl noticeRepositoryDsl;
	@Autowired
	ModelMapper modelMapper;
	@PersistenceContext
	protected EntityManager entityManager;
	@Autowired
	UploadFileUtil uploadFileUtil;
	@Autowired
    AuthService authService;
	@Autowired
	AttachFileRepository attachFileRepository;

	public Page<NoticeDTO> list(NoticeSearchDTO noticeSearchDTO) {

		return noticeRepositoryDsl
				.noticeList(noticeSearchDTO, PageRequest.of(noticeSearchDTO.getCurrentPage()-1, noticeSearchDTO.getLimit() /*, Sort.by("id")*/ ))
				.map(notice -> modelMapper.map(notice, NoticeDTO.class));
	}

	public Notice one(Long id) {
		Optional<Notice> findById = noticeRepository.findById(id);
		return findById.get();
	}

	public Map<String, Object> save(Notice notice, List<MultipartFile> files) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			if(authService.getAuth().isAdmin()) {
				notice.setMember(authService.getAuth().getMember());
				Notice save = noticeRepository.save(notice);
				List<AttachFile> attachFiles = files.stream().map(mf -> {
					AttachFile attachFile = null;
					try {
						attachFile = uploadFileUtil.fileUpload(mf.getOriginalFilename(), mf.getBytes());
						entityManager.persist(attachFile);
						return attachFile;
					} catch (Exception e) {
						e.printStackTrace();
						return attachFile;
					}
				}).collect(Collectors.toList());

				save.setAttachFile(attachFiles);
				map.put("id", save.getId());
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

	public Map<String, Object> modify(final Notice notice, List<MultipartFile> files) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(authService.getAuth().isAdmin()) {
				Optional<Notice> findNotice = noticeRepository.findById(notice.getId());
				findNotice.ifPresent(nt->{
					nt.setContent(notice.getContent());
					nt.setTitle(notice.getTitle());
					nt.setUpdateDate(new Date());
				});

				files.stream().forEach(mf -> {
					AttachFile attachFile = null;
					try {
						attachFile = uploadFileUtil.fileUpload(mf.getOriginalFilename(), mf.getBytes());
						entityManager.persist(attachFile);
						findNotice.get().getAttachFile().add(attachFile);
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
		Optional<Notice> notice = noticeRepository.findById(id);
		try {
			if(authService.getAuth().isAdmin()) {
				notice.ifPresent(no -> {
					no.setDeleteYN("Y");
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

	
	public Map<String, Object> authFileDelete(Long noticeId, Long fileId) {
    	
    	AuthDTO auth = authService.getAuth();
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	Optional<Notice> notice = noticeRepository.findById(noticeId);
    	Optional<AttachFile> attachFile = attachFileRepository.findById(fileId);
    	
		if(auth.isAdmin()) {
			List<AttachFile> attachFiles = attachFile.map(af->{
	    		File file = new File(af.getFullPath()+File.separator+af.getVirtualName());
	    		if(file.exists()) {
	    			file.delete();
	    		}
	    		notice.get().getAttachFile().remove(af);
	    		
	    		return notice.get().getAttachFile();
	    	}).get();
			map.put("result", "success");
			map.put("msg", "삭제되었습니다.");
			map.put("attachFile", attachFiles);
		}else {
			map.put("result", "fail");
			map.put("msg", "관리자만 삭제할 수 있습니다.");
		}
		return map;
    	
    }
    
  

}
