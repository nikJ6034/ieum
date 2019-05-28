package com.eum.program.repository;

import com.eum.program.dto.ProgramSearchDTO;
import com.eum.program.entity.Program;
import com.eum.program.entity.QProgram;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class ProgramRepositoryDsl extends QuerydslRepositorySupport {

    public ProgramRepositoryDsl() {
        super(Program.class);
    }

    public Page<Program> programList(ProgramSearchDTO programSearchDTO){
        QProgram program= QProgram.program;
        Pageable pageable = programSearchDTO.getPageable();

        BooleanBuilder builder = new BooleanBuilder();

        if(!StringUtils.isEmpty(programSearchDTO.getTitle())) {
            builder.and(program.title.contains(programSearchDTO.getTitle()));
        }

        if(programSearchDTO.getKind() != null && programSearchDTO.getKind() > 0){
            builder.and(program.kind.eq(programSearchDTO.getKind()));
        }

        JPQLQuery<Program> list = from(program)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(program.id.desc())
                .where(builder, program.deleteYN.eq("N"));

        return new PageImpl<>(list.fetch(), pageable, list.fetchCount());
    }
}
