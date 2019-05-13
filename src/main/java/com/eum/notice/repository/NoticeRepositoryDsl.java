package com.eum.notice.repository;

import com.eum.freeBoard.entity.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.eum.bbs.entity.QBbs;
import com.eum.member.entity.QMember;
import com.eum.notice.dto.NoticeSearchDTO;
import com.eum.notice.entity.Notice;
import com.eum.notice.entity.QNotice;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

@Repository
public class NoticeRepositoryDsl extends QuerydslRepositorySupport{

	public NoticeRepositoryDsl() {
		super(Notice.class);
	}

	public Page<Notice> noticeList(NoticeSearchDTO noticeSearchDTO, Pageable pageable){
		QBbs bbs = QBbs.bbs;
		QMember member = QMember.member;
		QNotice notice= QNotice.notice;
		BooleanBuilder builder = new BooleanBuilder();
		
		if(!StringUtils.isEmpty(noticeSearchDTO.getTitle())) {
			builder.and(bbs.title.contains(noticeSearchDTO.getTitle()));
		}
		
		JPQLQuery<Notice> list = from(notice)
		.leftJoin(bbs)
		.on(notice.bbs.eq(bbs))
		.leftJoin(member)
		.on(bbs.member.eq(member))
		.limit(pageable.getPageSize())
		.offset(pageable.getOffset())
		.orderBy(notice.id.desc())
		.where(builder, bbs.useYN.eq("Y"));
		
		return new PageImpl<>(list.fetch(), pageable, list.fetchCount());
	}

	public Notice modify(Notice notice){

		getEntityManager().merge(notice.getBbs());
		Notice modify = getEntityManager().find(Notice.class, notice.getId());
		return modify;
	}
	
}
