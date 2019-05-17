package com.eum.freeBoard.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eum.bbs.entity.Bbs;
import com.eum.config.oAuth2.AuthService;
import com.eum.freeBoard.dto.FreeBoardDTO;
import com.eum.freeBoard.dto.FreeBoardSearchDTO;
import com.eum.freeBoard.entity.FreeBoard;
import com.eum.freeBoard.repository.FreeBoardRepository;
import com.eum.freeBoard.repository.FreeBoardRepositoryDsl;
import com.eum.member.entity.Member;

@Service
@Transactional
public class FreeBoardService {

	@Autowired
	FreeBoardRepositoryDsl freeBoardRepositoryDsl;
	@Autowired
	FreeBoardRepository freeBoardRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
    AuthService authService;
	@PersistenceContext
	protected EntityManager entityManager;
	
	public Page<FreeBoardDTO> list(FreeBoardSearchDTO freeBoardSearchDTO){
		return freeBoardRepositoryDsl
				.freeBoardList(freeBoardSearchDTO, PageRequest.of(freeBoardSearchDTO.getCurrentPage()-1, freeBoardSearchDTO.getLimit() /*, Sort.by("id")*/ ))
				.map(freeBoard -> modelMapper.map(freeBoard, FreeBoardDTO.class));
	}
	
	public FreeBoard one(Long id) {
		Optional<FreeBoard> findById = freeBoardRepository.findById(id);
		return findById.get();
	}
	
	public Map<String, Object> save(FreeBoard freeBoard) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Member member = new Member();
			member.setId(authService.getAuth().getMemberId());
			freeBoard.setMember(member);
			FreeBoard save = freeBoardRepository.save(freeBoard);
				map.put("id", save.getId());
				map.put("msg", "저장 되었습니다.");
				map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("msg", "exception");
			return 	map;
		}
		
		return map;
	}
	
	public Map<String, Object> modify(FreeBoard freeBoard) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Optional<FreeBoard> findFreeBoard = freeBoardRepository.findById(freeBoard.getId());


			if(authService.getAuth().permission(findFreeBoard.get().getMember().getId())) {
				findFreeBoard.ifPresent(fb->{
					fb.setContent(freeBoard.getContent());
					fb.setTitle(freeBoard.getTitle());
					fb.setUpdateDate(new Date());
				});
				map.put("msg", "저장 되었습니다.");
				map.put("result", "success");
			}else {
				map.put("msg", "권한이 없습니다.");
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
		Optional<FreeBoard> findFreeBoard = freeBoardRepository.findById(id);
		try {
			if(authService.getAuth().permission(findFreeBoard.get().getMember().getId())) {
				findFreeBoard.ifPresent(fb -> {
					fb.setDeleteYN("Y");
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
}
