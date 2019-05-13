package com.eum.socialLogin;

import com.eum.member.entity.Member;

public interface ISocialAuth {
	public Member userInfo() throws Exception;
}
