package com.eum.activity.repository;

import com.eum.activity.dto.ActivitySearchDTO;
import com.eum.activity.entity.Activity;
import com.eum.activity.entity.QActivity;
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
public class ActivityRepositoryDsl extends QuerydslRepositorySupport {

	public ActivityRepositoryDsl() {
		super(Activity.class);
	}
	
	public Page<Activity> activityList(ActivitySearchDTO activitySearchDTO, Pageable pageable){
		QMember member = QMember.member;
		QActivity activity= QActivity.activity;
		BooleanBuilder builder = new BooleanBuilder();
		
		if(!StringUtils.isEmpty(activitySearchDTO.getTitle())) {
			builder.and(activity.title.contains(activitySearchDTO.getTitle()));
		}
		
		JPQLQuery<Activity> list = from(activity)
		.leftJoin(member)
		.on(activity.member.eq(member))
		.limit(pageable.getPageSize())
		.offset(pageable.getOffset())
		.orderBy(activity.id.desc())
		.where(builder, activity.deleteYN.eq("N"));
		
		return new PageImpl<>(list.fetch(), pageable, list.fetchCount());
	}
}
