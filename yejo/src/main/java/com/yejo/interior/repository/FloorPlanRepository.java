package com.yejo.interior.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yejo.interior.entity.FloorPlanEntity;

public interface FloorPlanRepository extends JpaRepository<FloorPlanEntity, Long>{
	List<FloorPlanEntity> findByConsultantId(Long consultantId);
}
