package com.eum.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eum.auth.dto.AuthDTO;
import com.eum.auth.jwt.JwtInfo;
import com.eum.auth.jwt.JwtUser;
import com.eum.auth.jwt.util.JwtUtil;
import com.eum.config.oAuth2.AuthService;
import com.eum.member.entity.Member;
import com.eum.member.service.MemberService;
import com.eum.menu.entity.Menu;
import com.eum.menu.entity.MenuRole;
import com.eum.menu.service.MenuRoleService;
import com.eum.menu.service.MenuService;
import com.eum.socialLogin.ISocialAuth;
import com.eum.socialLogin.SocialLogin;

@RestController
@RequestMapping(value = "/api/web")
public class MemberController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	MenuRoleService menuRoleService;
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	AuthService authService;

	/*
	 * @RequestMapping(value = "/login", method = RequestMethod.POST) public
	 * Map<String, Object> login(@RequestBody Member member, HttpSession session) {
	 * Map<String, Object> map = new HashMap<String, Object>();
	 * UsernamePasswordAuthenticationToken token = new
	 * UsernamePasswordAuthenticationToken(member.getMemberName(),
	 * member.getMemberPassword()); Authentication authentication =
	 * authenticationManager.authenticate(token);
	 * SecurityContextHolder.getContext().setAuthentication(authentication);
	 * map.put("session", SecurityContextHolder.getContext());
	 * 
	 * return map; }
	 */
	
	@RequestMapping(value = "/login/social", method = RequestMethod.GET)
	public Map<String, Object> logout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String access_token = (String)request.getParameter("access_token");
		ISocialAuth create = SocialLogin.create("kakao",access_token);
		
		map.put("userInfo", create.userInfo());
		return map;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public Map<String, Object> logout(Principal principal, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Optional<SecurityContext> securityContext = Optional.ofNullable((SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY));
		Optional<Authentication> auth = securityContext.map(sc -> sc.getAuthentication());

		auth.ifPresent(a -> {
			new SecurityContextLogoutHandler().logout(request, response, a);
		});
		System.out.println("로그아웃");
		return map;
	}


	@RequestMapping(value = "/insertUser")
	public Map<String, Object> insertUser(Member member) {

		Map<String, Object> map = new HashMap<String, Object>();
		Optional<String> memberId = Optional.ofNullable(member).map(Member::getMemberName);
		if (memberId.isPresent()) {
			Member insertMember = memberService.insertMember(member);
			map.put("member", insertMember);
		} else {
			map.put("result", "아이디가 존재하지 않습니다.");
		}

		return map;

	}
	
	@RequestMapping(value = "/roleState", method = RequestMethod.GET)
	public Map<String, Object> memberInfo(Principal principal, HttpSession session, HttpServletRequest request, String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		MenuRole menuRole = new MenuRole();
		Menu menu = menuService.getMenuByUrl(url);
		AuthDTO auth = authService.getAuth();
		if(auth.isAdmin()){
			menuRole.setDeleteRole("Y");
			menuRole.setModifyRole("Y");
			menuRole.setReadRole("Y");
			menuRole.setWriteRole("Y");
		}else{
			menuRole = menuRoleService.getMenuRole(auth.getAuthorities(), menu);
		}

		map.put("id", auth.getMemberId());
		map.put("isAdmin", auth.isAdmin());
		map.put("menuRole", menuRole);
		return map;
	}
	
	@RequestMapping("/refresh")
	public ResponseEntity<String> refreshToken() {
		AuthDTO auth = authService.getAuth();
		JwtUser userDetails = new JwtUser(auth.getMemberId(), auth.getMemberName(), new ArrayList<>(auth.getAuthorities()));

		String token = JwtUtil.refreshToken(userDetails);

		HttpHeaders headers = new HttpHeaders();
		headers.add(JwtInfo.HEADER_NAME, token);

		return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
	}
}
