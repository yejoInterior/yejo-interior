package com.yejo.interior.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yejo.interior.dto.ReviewDto;
import com.yejo.interior.entity.Review;
import com.yejo.interior.repository.ReviewRepository;
import com.yejo.interior.utility.FileUtility;

@Service
public class ReviewService {
	
	private static final Logger log = LoggerFactory.getLogger(ReviewService.class);

	@Autowired
	private FileUtility fileUtility;
	
	@Autowired
	private ReviewRepository reviewRepository; // JPA Repository

	@Value("${upload.path}")
	private String uploadPath;// 이미지 저장 경로

	//리뷰 작성 이미지 저장
	public String saveImage(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new IllegalArgumentException("파일이 비어있습니다.");
			}

			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			Path path = Paths.get(uploadPath, fileName);
			Files.createDirectories(path.getParent()); // 디렉토리 생성

			// 이미지 파일 저장
			Files.copy(file.getInputStream(), path);

			// 이미지 경로와 파일 이름을 반환
			return fileName;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("이미지 저장 중 오류가 발생했습니다: " + e.getMessage());
		}
	}

	// 리뷰 작성
	public void saveReview(ReviewDto reviewDto) throws IOException {
	    try {
	        // 이미지 저장
	        Map<String, Object> uploadResponse = fileUtility.uploadFile(reviewDto.getImage(), "reviews"); // 디렉토리 이름은 적절히 수정하세요

	        // 성공적으로 파일이 업로드 되었는지 확인
	        if (!(Boolean) uploadResponse.get("success")) {
	            throw new RuntimeException("이미지 업로드 실패: " + uploadResponse.get("message"));
	        }

	        String imagePath = (String) uploadResponse.get("filePath");

	        // Review 생성
	        Review review = new Review();
	        review.setTitle(reviewDto.getTitle());

	        if (reviewDto.getTags() != null && !reviewDto.getTags().isEmpty()) {
	            String tags = String.join(",", reviewDto.getTags()); // ","로 결합
	            review.setTag(tags); // 태그 설정
	        } else {
	            review.setTag(""); // 태그가 없으면 빈 문자열로 설정
	        }

	        review.setContent(reviewDto.getContent());
	        review.setUrl(reviewDto.getUrl());
	        review.setImagePath(imagePath);

	        // 데이터베이스에 저장
	        reviewRepository.save(review);

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("리뷰 저장 중 오류가 발생했습니다: " + e.getMessage());
	    }
	}


	// 리뷰 한 개 가져오기
	public Review getOneReview(Integer id) {
	    return reviewRepository.findById(id).orElseThrow(() -> 
	        new RuntimeException("리뷰를 찾을 수 없습니다. ID: " + id)
	    );
	}
	
	// 리뷰 가져오기
	public List<Review> getAllReview() {
		try {
			return reviewRepository.findAllByOrderByIdxDesc();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("리뷰를 가져오는 중 오류가 발생했습니다.", e);
		}
	}	
	
	// 리뷰 가져오기(페이징)
	public List<Review> getReviews(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("idx").descending());
        Page<Review> pageResult = reviewRepository.findAll(pageable);
        return pageResult.getContent();
    }
	
	// 리뷰 삭제 서비스 로직
	public void deleteReview(Integer reviewId) {
	    try {
	        // 리뷰 찾기
	        Review review = reviewRepository.findById(reviewId)
	            .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));

	        // CDN에서 이미지 파일 삭제
	        String imagePath = review.getImagePath();
	        boolean isDeletedFromCDN = fileUtility.deleteFileFromCDN(imagePath);
	        
	        if (!isDeletedFromCDN) {
	            log.warn("CDN에서 파일을 삭제하지 못했습니다: " + imagePath);
	        }

	        // 데이터베이스에서 리뷰 삭제
	        reviewRepository.delete(review);
	    } catch (Exception e) {
	        log.error("리뷰를 삭제하는 중 오류가 발생했습니다: " + e.getMessage(), e);
	        throw new RuntimeException("리뷰를 삭제하는 중 오류가 발생했습니다.", e);
	    }
	}
    
    public void updateReview(Integer id, ReviewDto reviewDto) {
        try {
            // 리뷰가 존재하는지 확인
            Optional<Review> optionalReview = reviewRepository.findById(id);
            
            // 리뷰가 존재하지 않는 경우
            if (!optionalReview.isPresent()) {
                throw new RuntimeException("리뷰를 찾을 수 없습니다.");
            }

            // 존재하는 리뷰 가져오기
            Review existingReview = optionalReview.get();

            // 업데이트된 필드로 기존 리뷰를 수정
            existingReview.setTitle(reviewDto.getTitle());
            
            if (reviewDto.getTags() != null && !reviewDto.getTags().isEmpty()) {
				String tags = String.join(",", reviewDto.getTags()); // ","로 결합
				existingReview.setTag(tags); // 태그 설정
			} else {
				existingReview.setTag(""); // 태그가 없으면 빈 문자열로 설정
			}
            
            existingReview.setUrl(reviewDto.getUrl());
            existingReview.setContent(reviewDto.getContent());
            
         // 새로운 파일이 첨부된 경우
            if (reviewDto.getImage() != null && !reviewDto.getImage().isEmpty()) {
                // 기존 이미지 파일 삭제
                String existingImagePath = existingReview.getImagePath();
    	        boolean isDeletedFromCDN = fileUtility.deleteFileFromCDN(existingImagePath);
    	        
    	        if (!isDeletedFromCDN) {
    	            log.warn("CDN에서 파일을 삭제하지 못했습니다: " + existingImagePath);
    	        }
                
                // 이미지 저장
    	        Map<String, Object> uploadResponse = fileUtility.uploadFile(reviewDto.getImage(), "reviews"); // 디렉토리 이름은 적절히 수정하세요

    	        // 성공적으로 파일이 업로드 되었는지 확인
    	        if (!(Boolean) uploadResponse.get("success")) {
    	            throw new RuntimeException("이미지 업로드 실패: " + uploadResponse.get("message"));
    	        }

    	        String imagePath = (String) uploadResponse.get("filePath");
                existingReview.setImagePath(imagePath); // DB에 새 이미지 파일 이름 업데이트
            }

            // 변경된 리뷰 저장
            reviewRepository.save(existingReview);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("리뷰를 업데이트하는 중 오류가 발생했습니다.", e);
        }
    }
}
