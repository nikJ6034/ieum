package com.eum.activity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.eum.activity.dto.ActivitySearchDTO;
import com.eum.activity.entity.Activity;
import com.eum.activity.entity.QActivity;
import com.eum.bbs.entity.QBbs;
import com.eum.member.entity.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

@Repository
public class ActivityRepositoryDsl extends QuerydslRepositorySupport {

	public ActivityRepositoryDsl() {
		super(Activity.class);
	}
	
	public Page<Activity> activityList(ActivitySearchDTO activitySearchDTO, Pageable pageable){
		QBbs bbs = QBbs.bbs;
		QMember member = QMember.member;
		QActivity activity= QActivity.activity;
		BooleanBuilder builder = new BooleanBuilder();
		
		if(!StringUtils.isEmpty(activitySearchDTO.getTitle())) {
			builder.and(bbs.title.contains(activitySearchDTO.getTitle()));
		}
		
		JPQLQuery<Activity> list = from(activity)
		.leftJoin(bbs)
		.on(activity.bbs.eq(bbs))
		.leftJoin(member)
		.on(bbs.member.eq(member))
		.limit(pageable.getPageSize())
		.offset(pageable.getOffset())
		.orderBy(activity.id.desc())
		.where(builder, bbs.useYN.eq("Y"));
		
		return new PageImpl<>(list.fetch(), pageable, list.fetchCount());
	}

	public Activity modify(Activity activity){

		getEntityManager().merge(activity.getBbs());
		Activity modify = getEntityManager().find(Activity.class, activity.getId());
		return modify;
	}
	
	
}
