package com.eum.program.repository;

import com.eum.program.entity.Program;
import com.eum.program.entity.ProgramApplication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ProgramApplicationRepository extends PagingAndSortingRepository<ProgramApplication, Long> {
    @Query("select count(pa.id) from ProgramApplication pa where pa.memberId = :memberId and pa.program = :program and pa.deleteYN = 'N'")
    Long exists(@Param("memberId") Long memberId, @Param("program") Program program);

    @Query("select pa from ProgramApplication pa where pa.memberId = :memberId and pa.program = :program and pa.deleteYN = 'N'")
    ProgramApplication findByMemberProgram(@Param("memberId") Long memberId, @Param("program") Program program);

    @Query("select count(pa.id) from ProgramApplication pa where pa.program = :program and pa.deleteYN = 'N'")
    Long appCount(@Param("program") Program program);
}
