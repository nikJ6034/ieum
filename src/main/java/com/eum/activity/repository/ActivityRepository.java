package com.eum.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eum.activity.entity.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
