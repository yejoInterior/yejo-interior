package com.yejo.interior.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yejo.interior.entity.FloorPlanEntity;
import com.yejo.interior.entity.ReferenceEntity;

public interface ReferenceRepository extends JpaRepository<ReferenceEntity, Long>{
	List<ReferenceEntity> findByConsultantId(Long consultantId);
}

