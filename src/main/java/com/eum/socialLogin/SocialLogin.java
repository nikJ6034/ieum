package com.eum.socialLogin;

public class SocialLogin {
	public static ISocialAuth create(String social, String token) {
		if("naver".equals(social)) {
//			return new Naver(token);
			return null;
		}else if("kakao".equals(social)) {
			return new Kakao(token);
		}else if("facebook".equals(social)) {
//			return new FaceBook(token);
			return null;
		}else if("google".equals(social)) {
//			return  new Google(token);
			return null;
		}else {
			return null;
		}
	}
}
