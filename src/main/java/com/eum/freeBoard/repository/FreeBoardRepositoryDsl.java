package com.eum.freeBoard.repository;

import com.eum.freeBoard.dto.FreeBoardSearchDTO;
import com.eum.freeBoard.entity.FreeBoard;
import com.eum.freeBoard.entity.QFreeBoard;
import com.eum.member.entity.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class FreeBoardRepositoryDsl extends QuerydslRepositorySupport{
	public FreeBoardRepositoryDsl() {
		super(FreeBoard.class);
	}
	
	public Page<FreeBoard> freeBoardList(FreeBoardSearchDTO freeBoardSearchDTO, Pageable pageable){
		QMember member = QMember.member;
		QFreeBoard freeboard = QFreeBoard.freeBoard;

		BooleanBuilder builder = new BooleanBuilder();
		
		if(!StringUtils.isEmpty(freeBoardSearchDTO.getTitle())) {
			builder.and(freeboard.title.contains(freeBoardSearchDTO.getTitle()));
		}
		
		JPQLQuery<FreeBoard> list = from(freeboard)
				.leftJoin(member)
				.on(freeboard.member.eq(member))
				.limit(pageable.getPageSize())
				.offset(pageable.getOffset())
				.orderBy(freeboard.id.desc())
				.where(builder, freeboard.deleteYN.eq("N"));
		
		return new PageImpl<>(list.fetch(), pageable, list.fetchCount());
	}
}
