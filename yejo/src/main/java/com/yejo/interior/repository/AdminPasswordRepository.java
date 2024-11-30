package com.yejo.interior.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yejo.interior.entity.AdminPasswordEntity;

@Repository
public interface AdminPasswordRepository extends JpaRepository<AdminPasswordEntity, Integer>{
	Optional<AdminPasswordEntity> findById(Integer id); // id로 레코드 찾기
}
