package com.yejo.interior.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yejo.interior.entity.KakaoTokenEntity;

public interface KakaoTokenRepository extends JpaRepository<KakaoTokenEntity, Long>{
	KakaoTokenEntity findFirstByOrderByIdDesc();
}
