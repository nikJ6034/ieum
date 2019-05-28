package com.eum.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.eum.auth.dto.AuthDTO;
import com.eum.config.oAuth2.AuthService;
import com.eum.member.dto.SignupDTO;
import com.eum.member.service.MemberService;
import com.eum.menu.entity.Menu;
import com.eum.menu.entity.MenuRole;
import com.eum.menu.service.MenuRoleService;
import com.eum.menu.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
	/*@RequestMapping(value = "/login/social", method = RequestMethod.GET)
	public Map<String, Object> logout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String access_token = (String)request.getParameter("access_token");
		ISocialAuth create = SocialLogin.create("kakao",access_token);
		
		map.put("userInfo", create.userInfo());
		return map;
	}*/

	/*@RequestMapping(value = "/logout", method = RequestMethod.GET)
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
	}*/


	@RequestMapping(value = "/signup", method=RequestMethod.POST)
	public Map<String, Object> insertUser(@RequestBody SignupDTO signupDTO) {
		Map<String, Object> map = new HashMap<>();

		try{
			memberService.insertMember(signupDTO);
			map.put("result", "success");
			map.put("msg", "회원가입이 완료되었습니다.");
		}catch (Exception e){
			map.put("result", "fail");
			map.put("msg", "회원가입에 실패하였습니다.");
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

	@RequestMapping(value = "/existsMember", method = RequestMethod.GET)
	public Map<String, Object> existsMember(String memberName){
		Map<String, Object> map = new HashMap<>();

		if(memberService.existsMember(memberName) > 0){
			map.put("result","exists");
			map.put("msg","이미 등록된 아이디입니다.");
		}else{
			map.put("result","success");
			map.put("msg","등록 가능한 아이디입니다.");
		}

		return map;
	}
}
