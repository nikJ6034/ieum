package com.eum;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
	
	@Test
	public void password() {
			BCryptPasswordEncoder bcr = new BCryptPasswordEncoder();
			String result = bcr.encode("1234");  
			System.out.println("암호 === " + result);
	}
}
