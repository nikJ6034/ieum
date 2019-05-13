package com.eum.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eum.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	public Optional<Member> findByMemberName(String memberName);
	public Optional<Member> findByKakaoKey(String kakaoKey);
}
