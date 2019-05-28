package com.eum.dataRoom.repository;

import com.eum.dataRoom.dto.DataRoomSearchDTO;
import com.eum.dataRoom.entity.DataRoom;
import com.eum.dataRoom.entity.QDataRoom;
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
public class DataRoomRepositoryDsl extends QuerydslRepositorySupport{
	public DataRoomRepositoryDsl() {
		super(DataRoom.class);
	}
	
	public Page<DataRoom> freeBoardList(DataRoomSearchDTO dataRoomSearchDTO, Pageable pageable){
		QMember member = QMember.member;
		QDataRoom dataroom = QDataRoom.dataRoom;
		
		BooleanBuilder builder = new BooleanBuilder();
		
		if(!StringUtils.isEmpty(dataRoomSearchDTO.getTitle())) {
			builder.and(dataroom.title.contains(dataRoomSearchDTO.getTitle()));
		}
		
		JPQLQuery<DataRoom> list = from(dataroom)
				.leftJoin(member)
				.on(dataroom.member.eq(member))
				.limit(pageable.getPageSize())
				.offset(pageable.getOffset())
				.orderBy(dataroom.id.desc())
				.where(builder, dataroom.deleteYN.eq("N"));
		
		return new PageImpl<>(list.fetch(), pageable, list.fetchCount());
	}
}
