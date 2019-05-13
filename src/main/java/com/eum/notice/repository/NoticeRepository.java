package com.eum.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eum.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>{

}
