package com.yejo.interior.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yejo.interior.dto.ReviewDto;
import com.yejo.interior.entity.Review;
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
			// 서비스 호출
			reviewService.deleteReview(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("리뷰 삭제 중 오류가 발생했습니다: " + e.getMessage());
		}
	}

	@GetMapping("/reviews/{id}")
	public ResponseEntity<?> getReview(@PathVariable Integer id) {
		try {
			Review review = reviewService.getOneReview(id);
			List<String> tags = Arrays.asList(review.getTag().split(","));
			review.setTagList(tags); // Review 클래스에 tagList 필드 추가 필요
			return ResponseEntity.ok(review); // 200 OK와 함께 리뷰 반환
		} catch (RuntimeException e) {
			// RuntimeException 발생 시 404 NOT FOUND 반환
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("리뷰를 가져오는 중 오류가 발생했습니다: " + e.getMessage());
		} catch (Exception e) {
			// 다른 예외 발생 시 500 INTERNAL SERVER ERROR 반환
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
		}
	}

	@GetMapping("/reviews")
	public List<Review> getReviews(@RequestParam("page") int page) {
		int pageSize = 3;
		List<Review> reviews = reviewService.getReviews(page, pageSize);

		for (Review review : reviews) {
			// 태그 문자열을 리스트로 변환
			List<String> tags = Arrays.asList(review.getTag().split(","));
			review.setTagList(tags); // 태그 리스트 설정
		}

		return reviews; // 변환된 리뷰 리스트 반환
	}

	@PutMapping("/reviews/{id}")
	public ResponseEntity<?> updateReview(@PathVariable Integer id, @ModelAttribute ReviewDto reviewDto) {
		try {
			// 서비스 메서드 호출
			reviewService.updateReview(id, reviewDto);
			return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다."); // 200 OK와 함께 성공 메시지 반환
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("리뷰를 수정하는 중 오류가 발생했습니다: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
		}
	}

}
