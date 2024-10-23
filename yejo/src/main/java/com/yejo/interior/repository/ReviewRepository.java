package com.yejo.interior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yejo.interior.entity.Review;
import com.yejo.interior.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{

}
