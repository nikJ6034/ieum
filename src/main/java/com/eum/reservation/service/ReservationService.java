package com.eum.reservation.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eum.auth.dto.AuthDTO;
import com.eum.config.oAuth2.AuthService;
import com.eum.member.entity.Member;
import com.eum.reservation.dto.ReservationDTO;
import com.eum.reservation.dto.ReservationDateDTO;
import com.eum.reservation.entity.Reservation;
import com.eum.reservation.repository.ReservationRepository;
import com.eum.reservation.repository.ReservationRepositoryDsl;

@Transactional
@Service
public class ReservationService {
	
	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	ReservationRepositoryDsl reservationRepositoryDsl;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
    AuthService authService;
	
	
	public List<ReservationDTO> list(ReservationDateDTO reservationDateDTO){
		return reservationRepositoryDsl.list(reservationDateDTO).stream().map(re -> modelMapper.map(re, ReservationDTO.class)).collect(Collectors.toList());
	}
	
	public List<Reservation> listAdmin(ReservationDateDTO reservationDateDTO){
		return reservationRepositoryDsl.listAdmin(reservationDateDTO);
	}
	
	public List<ReservationDTO> listByOwn(ReservationDateDTO reservationDateDTO){
		Optional<AuthService> auth = Optional.ofNullable(authService);
		Long memberId = auth.map(AuthService::getAuth).map(AuthDTO::getMemberId).orElse(0L);
		reservationDateDTO.setMemberId(memberId);
		return reservationRepositoryDsl.listByOwn(reservationDateDTO).stream().map(re -> modelMapper.map(re, ReservationDTO.class)).collect(Collectors.toList());
	}
	
	public Map<String, Object> checkDate(Reservation reservation){
		Map<String, Object> map = new HashMap<String, Object>();
		
		return map;
	}
	
	public Map<String, Object> insert(Reservation reservation){
		Map<String, Object> map = new HashMap<String, Object>();
		long exists = reservationRepositoryDsl.exists(reservation);
		if(exists > 0) {
			map.put("msg", "이미 예약한 시간이 포함되어있습니다.");
			map.put("result", "exists");

		}else {
			Member member = new Member();
			member.setId(authService.getAuth().getMemberId());
			reservation.setMember(member);
			reservationRepository.save(reservation);
			map.put("msg", "저장 되었습니다.");
			map.put("result", "success");
		}
		
		return map;
	}
	
	public Map<String, Object> accept(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		Optional<Reservation> reservation = reservationRepository.findById(id);
		
		if(authService.getAuth().isAdmin()) {
			if(reservation.map(Reservation::getAcceptYN).get().equals("Y")) {
				reservation.get().setAcceptYN("N");
				map.put("msg", "예약을 승인을 취소하였습니다.");
				map.put("result", "success");
			}else {
				reservation.get().setAcceptYN("Y");
				map.put("msg", "예약을 승인하였습니다.");
				map.put("result", "success");
			}
			
			
		}else {
			map.put("msg", "관리자가 아닙니다.");
			map.put("result", "fail");
		}
		
		return map;
	}
	
	public Map<String, Object> cancel(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		Optional<Reservation> reservation = reservationRepository.findById(id);
		if(authService.getAuth().permission(reservation.get().getMember().getId())) {
			long exists = reservationRepositoryDsl.exists(reservation.get());
			
			if(reservation.map(Reservation::getDeleteYN).get().equals("N")) {
				reservation.get().setDeleteYN("Y");
				map.put("msg", "예약이 취소되었습니다.");
				map.put("result", "success");
			}else {
				if(exists > 0) {
					map.put("msg", "중복된 날짜가 존재합니다.");
					map.put("result", "fail");
				}else {
					reservation.get().setDeleteYN("N");
					map.put("msg", "예약이 복구되었습니다.");
					map.put("result", "success");
				}
			}
			
		}else {
			map.put("msg", "예약 취소가 실패하였습니다.");
			map.put("result", "fail");
		}
		
		return map;
	}
}
