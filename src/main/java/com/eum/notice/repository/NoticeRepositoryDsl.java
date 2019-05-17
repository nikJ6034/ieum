package com.eum.notice.repository;

import com.eum.member.entity.QMember;
import com.eum.notice.dto.NoticeSearchDTO;
import com.eum.notice.entity.Notice;
import com.eum.notice.entity.QNotice;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Date;

@Repository
public class NoticeRepositoryDsl extends QuerydslRepositorySupport{

	public NoticeRepositoryDsl() {
		super(Notice.class);
	}

	public Page<Notice> noticeList(NoticeSearchDTO noticeSearchDTO, Pageable pageable){
		QMember member = QMember.member;
		QNotice notice= QNotice.notice;
		BooleanBuilder builder = new BooleanBuilder();
		
		if(!StringUtils.isEmpty(noticeSearchDTO.getTitle())) {
			builder.and(notice.title.contains(noticeSearchDTO.getTitle()));
		}
		
		JPQLQuery<Notice> list = from(notice)
		.leftJoin(member)
		.on(notice.member.eq(member))
		.limit(pageable.getPageSize())
		.offset(pageable.getOffset())
		.orderBy(notice.id.desc())
		.where(builder, notice.deleteYN.eq("N"));
		
		return new PageImpl<>(list.fetch(), pageable, list.fetchCount());
	}

}
