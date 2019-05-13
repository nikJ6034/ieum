package com.eum.program.repository;

import com.eum.program.dto.ProgramApplicationSearchDTO;
import com.eum.program.entity.Program;
import com.eum.program.entity.ProgramApplication;
import com.eum.program.entity.QProgramApplication;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ProgramApplicationRepositoryDsl extends QuerydslRepositorySupport {
    public ProgramApplicationRepositoryDsl() {
        super(ProgramApplication.class);
    }

    public Page<ProgramApplication> programApplicationList(ProgramApplicationSearchDTO programApplicationSearchDTO){
        QProgramApplication programApplication= QProgramApplication.programApplication;
        Pageable pageable = programApplicationSearchDTO.getPageable();

        BooleanBuilder builder = new BooleanBuilder();

        JPQLQuery<ProgramApplication> list = from(programApplication)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(programApplication.id.desc())
                .where(builder, programApplication.deleteYN.eq("N"));

        return new PageImpl<>(list.fetch(), pageable, list.fetchCount());
    }
}
