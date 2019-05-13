package com.eum.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eum.config.oAuth2.AuthService;
import com.eum.reservation.dto.ReservationDateDTO;
import com.eum.reservation.entity.Reservation;
import com.eum.reservation.service.ReservationService;

@RestController
@RequestMapping(value = "/api/web")
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	@Autowired
    AuthService authService;
	
	@RequestMapping(value = "/reservation", method = RequestMethod.GET)
	public Map<String, Object> list(ReservationDateDTO reservationDateDTO) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(authService.getAuth().isAdmin()) {
			map.put("list", reservationService.listAdmin(reservationDateDTO));
		}else {
			map.put("list", reservationService.list(reservationDateDTO));
		}
		return map;
	}
	
	@RequestMapping(value = "/reservationByOwn", method = RequestMethod.GET)
	public Map<String, Object> listByOwn(ReservationDateDTO reservationDateDTO) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", reservationService.listByOwn(reservationDateDTO));
		return map;
	}
	
	@RequestMapping(value = "/reservation", method = RequestMethod.POST)
	public Map<String, Object> insert(@RequestBody Reservation reservation) {
		
		return reservationService.insert(reservation);
		
	}
	
	@RequestMapping(value = "/reservation/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable long id) {
		
		return reservationService.cancel(id);
		
	}
	
	@RequestMapping(value = "/reservation/accept/{id}", method = RequestMethod.PUT)
	public Map<String, Object> accept(@PathVariable long id) {
		return reservationService.accept(id);
		
	}
}
