package com.yejo.interior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yejo.interior.entity.ConsultantEntity;

import jakarta.transaction.Transactional;

public interface ConsultantRepository extends JpaRepository<ConsultantEntity, Long> {
	@Modifying
    @Transactional
    @Query("UPDATE ConsultantEntity e SET e.status = CASE WHEN e.status = '진행중' THEN '완료' ELSE '진행중' END WHERE e.id = :id")
    int toggleStatusById(Long id);
}
