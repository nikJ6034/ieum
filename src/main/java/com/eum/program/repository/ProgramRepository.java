package com.eum.program.repository;

import com.eum.program.entity.Program;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProgramRepository extends PagingAndSortingRepository<Program, Long> {

}
