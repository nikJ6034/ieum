package com.eum.freeBoard.repository;

import com.eum.dataRoom.entity.DataRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.eum.bbs.entity.QBbs;
import com.eum.freeBoard.dto.FreeBoardSearchDTO;
import com.eum.freeBoard.entity.FreeBoard;
import com.eum.freeBoard.entity.QFreeBoard;
import com.eum.member.entity.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

@Repository
public class FreeBoardRepositoryDsl extends QuerydslRepositorySupport{
	public FreeBoardRepositoryDsl() {
		super(FreeBoard.class);
	}
	
	public Page<FreeBoard> freeBoardList(FreeBoardSearchDTO freeBoardSearchDTO, Pageable pageable){
		QBbs bbs = QBbs.bbs;
		QMember member = QMember.member;
		QFreeBoard freeboard = QFreeBoard.freeBoard;
		
		BooleanBuilder builder = new BooleanBuilder();
		
		if(!StringUtils.isEmpty(freeBoardSearchDTO.getTitle())) {
			builder.and(bbs.title.contains(freeBoardSearchDTO.getTitle()));
		}
		
		JPQLQuery<FreeBoard> list = from(freeboard)
				.leftJoin(bbs)
				.on(freeboard.bbs.eq(bbs))
				.leftJoin(member)
				.on(bbs.member.eq(member))
				.limit(pageable.getPageSize())
				.offset(pageable.getOffset())
				.orderBy(freeboard.id.desc())
				.where(builder, bbs.useYN.eq("Y"));
		
		return new PageImpl<>(list.fetch(), pageable, list.fetchCount());
	}
	
	public FreeBoard modify(FreeBoard freeBoard){

		getEntityManager().merge(freeBoard.getBbs());
		FreeBoard modify = getEntityManager().find(FreeBoard.class, freeBoard.getId());
		return modify;
	}
}
