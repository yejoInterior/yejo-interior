package com.yejo.interior.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yejo.interior.dto.ReviewDto;
import com.yejo.interior.service.ReviewService;

@RestController
@RequestMapping("api")
public class ReviewRestController {

	private final ReviewService ReviewService;

    public ReviewRestController(ReviewService ReviewService) {
        this.ReviewService = ReviewService;
    }

    @PostMapping("/Reviews")
    public ResponseEntity<String> createReview(@ModelAttribute ReviewDto ReviewDto) {
        try {
            ReviewService.saveReview(ReviewDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("후기가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("후기 저장 중 오류가 발생했습니다.");
        }
    }
}
