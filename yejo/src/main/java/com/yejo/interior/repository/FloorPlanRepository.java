package com.yejo.interior.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yejo.interior.entity.FloorPlanEntity;

public interface FloorPlanRepository extends JpaRepository<FloorPlanEntity, Long>{
	List<FloorPlanEntity> findByConsultantId(Long consultantId);
	void deleteByConsultantId(Long consultantId);
	@Query("SELECT f.storagePath FROM FloorPlanEntity f WHERE f.consultant.id = :consultantId")
    List<String> findStoragePathsByConsultantId(@Param("consultantId") Long consultantId);
}
