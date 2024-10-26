package com.yejo.interior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yejo.interior.entity.PortfolioEntity;

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long>, PagingAndSortingRepository<PortfolioEntity, Long> {
}


