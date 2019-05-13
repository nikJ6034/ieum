package com.eum.dataRoom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.eum.bbs.entity.QBbs;
import com.eum.dataRoom.dto.DataRoomSearchDTO;
import com.eum.dataRoom.entity.DataRoom;
import com.eum.dataRoom.entity.QDataRoom;
import com.eum.member.entity.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

@Repository
public class DataRoomRepositoryDsl extends QuerydslRepositorySupport{
	public DataRoomRepositoryDsl() {
		super(DataRoom.class);
	}
	
	public Page<DataRoom> freeBoardList(DataRoomSearchDTO dataRoomSearchDTO, Pageable pageable){
		QBbs bbs = QBbs.bbs;
		QMember member = QMember.member;
		QDataRoom dataroom = QDataRoom.dataRoom;
		
		BooleanBuilder builder = new BooleanBuilder();
		
		if(!StringUtils.isEmpty(dataRoomSearchDTO.getTitle())) {
			builder.and(bbs.title.contains(dataRoomSearchDTO.getTitle()));
		}
		
		JPQLQuery<DataRoom> list = from(dataroom)
				.leftJoin(bbs)
				.on(dataroom.bbs.eq(bbs))
				.leftJoin(member)
				.on(bbs.member.eq(member))
				.limit(pageable.getPageSize())
				.offset(pageable.getOffset())
				.orderBy(dataroom.id.desc())
				.where(builder, bbs.useYN.eq("Y"));
		
		return new PageImpl<>(list.fetch(), pageable, list.fetchCount());
	}
	
	public DataRoom modify(DataRoom dataRoom){

		getEntityManager().merge(dataRoom.getBbs());
		DataRoom modify = getEntityManager().find(DataRoom.class, dataRoom.getId());
		return modify;
	}
}
