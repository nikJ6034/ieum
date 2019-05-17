package com.eum.dataRoom.service;

import java.io.File;
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

import com.eum.attachFile.entity.AttachFile;
import com.eum.attachFile.repository.AttachFileRepository;
import com.eum.auth.dto.AuthDTO;
import com.eum.bbs.entity.Bbs;
import com.eum.config.oAuth2.AuthService;
import com.eum.dataRoom.dto.DataRoomDTO;
import com.eum.dataRoom.dto.DataRoomSearchDTO;
import com.eum.dataRoom.entity.DataRoom;
import com.eum.dataRoom.repository.DataRoomRepository;
import com.eum.dataRoom.repository.DataRoomRepositoryDsl;
import com.eum.util.file.UploadFileUtil;

@Service
@Transactional
public class DataRoomService {

	@Autowired
	DataRoomRepositoryDsl dataRoomRepositoryDsl;
	@Autowired
	DataRoomRepository dataRoomRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
    AuthService authService;
	@Autowired
	UploadFileUtil uploadFileUtil;
	@PersistenceContext
	protected EntityManager entityManager;
	@Autowired
	AttachFileRepository attachFileRepository;
	
	public Page<DataRoomDTO> list(DataRoomSearchDTO dataRoomSearchDTO){
		return dataRoomRepositoryDsl
				.freeBoardList(dataRoomSearchDTO, PageRequest.of(dataRoomSearchDTO.getCurrentPage()-1, dataRoomSearchDTO.getLimit() /*, Sort.by("id")*/ ))
				.map(freeBoard -> modelMapper.map(freeBoard, DataRoomDTO.class));
	}
	
	public DataRoom one(Long id) {
		Optional<DataRoom> findById = dataRoomRepository.findById(id);
		return findById.get();
	}
	
	public Map<String, Object> save(DataRoom dataRoom, List<MultipartFile> files) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			if(authService.getAuth().isAdmin()) {
				dataRoom.setMember(authService.getAuth().getMember());
				DataRoom save = dataRoomRepository.save(dataRoom);
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

	public Map<String, Object> modify(DataRoom dataRoom, List<MultipartFile> files) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(authService.getAuth().isAdmin()) {
				Optional<DataRoom> findDataRoom = dataRoomRepository.findById(dataRoom.getId());

				findDataRoom.ifPresent(dr->{
					dr.setContent(dataRoom.getContent());
					dr.setTitle(dataRoom.getTitle());
					dr.setUpdateDate(new Date());
				});

				files.stream().forEach(mf -> {
					try {
						AttachFile attachFile = uploadFileUtil.fileUpload(mf.getOriginalFilename(), mf.getBytes());
						entityManager.persist(attachFile);
						findDataRoom.get().getAttachFile().add(attachFile);
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
		Optional<DataRoom> dataRoom = dataRoomRepository.findById(id);
		try {
			if(authService.getAuth().isAdmin()) {
				dataRoom.ifPresent(da -> {
					da.setDeleteYN("Y");

					for(AttachFile aFile : da.getAttachFile()){
						File file = new File(aFile.getFullPath()+File.separator+aFile.getVirtualName());
						if(file.exists()) {
							file.delete();
						}
						attachFileRepository.delete(aFile);
					}

					entityManager.persist(da);
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

	
	public Map<String, Object> authFileDelete(Long dataRoomId, Long fileId) {
    	
    	AuthDTO auth = authService.getAuth();
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	Optional<DataRoom> dataRoom = dataRoomRepository.findById(dataRoomId);
    	Optional<AttachFile> attachFile = attachFileRepository.findById(fileId);
    	
		if(auth.isAdmin()) {
			List<AttachFile> attachFiles = attachFile.map(af->{
	    		File file = new File(af.getFullPath()+File.separator+af.getVirtualName());
	    		if(file.exists()) {
	    			file.delete();
	    		}
	    		dataRoom.get().getAttachFile().remove(af);
	    		
	    		return dataRoom.get().getAttachFile();
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
