package com.yejo.interior.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yejo.interior.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
	List<Review> findAllByOrderByIdxDesc();
	

}
