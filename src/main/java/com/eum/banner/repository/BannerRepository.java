package com.eum.banner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eum.banner.entity.Banner;

public interface BannerRepository extends JpaRepository<Banner, Long>{

}
