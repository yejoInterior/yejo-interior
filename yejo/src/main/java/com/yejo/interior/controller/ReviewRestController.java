package com.yejo.interior.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yejo.interior.dto.ReviewDto;
import com.yejo.interior.service.ReviewService;

@RestController
@RequestMapping("api")
public class ReviewRestController {

	private final ReviewService reviewService;

    public ReviewRestController(ReviewService ReviewService) {
        this.reviewService = ReviewService;
    }

    @PostMapping("/reviews")
    public ResponseEntity<String> createReview(@ModelAttribute ReviewDto ReviewDto) {
        try {
            reviewService.saveReview(ReviewDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("후기가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("후기 저장 중 오류가 발생했습니다.");
        }
    }
    
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        try {
        	System.out.println(id);
            // 서비스 호출
            reviewService.deleteReview(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("리뷰 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
