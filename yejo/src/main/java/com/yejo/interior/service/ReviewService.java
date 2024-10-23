package com.yejo.interior.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yejo.interior.dto.ReviewDto;
import com.yejo.interior.entity.Review;
import com.yejo.interior.repository.ReviewRepository;

@Service
public class ReviewService {
	
	@Autowired
    private ReviewRepository ReviewRepository; // JPA Repository

	@Value("${upload.path}")
    private String IMAGE_UPLOAD_DIR;// 이미지 저장 경로

	public String saveImage(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("파일이 비어있습니다.");
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(IMAGE_UPLOAD_DIR, fileName);
            Files.createDirectories(path.getParent()); // 디렉토리 생성

            // 이미지 파일 저장
            Files.copy(file.getInputStream(), path);
            return fileName; // 저장된 파일 이름 반환

        } catch (IOException e) {
            // 예외 발생 시 로그 기록 및 사용자에게 전달할 메시지 생성
            e.printStackTrace();
            throw new RuntimeException("이미지 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public void saveReview(ReviewDto ReviewDto) throws IOException {
    	try {
            // 이미지 저장
            String imagePath = saveImage(ReviewDto.getImage());

            // Review 생성
            Review Review = new Review();
            Review.setTitle(ReviewDto.getTitle());
            
            if (ReviewDto.getTags() != null && !ReviewDto.getTags().isEmpty()) {
                String tags = String.join(",", ReviewDto.getTags()); // ","로 결합
                Review.setTag(tags); // 태그 설정
            } else {
                Review.setTag(""); // 태그가 없으면 빈 문자열로 설정
            }

            Review.setContent(ReviewDto.getContent());
            Review.setUrl(ReviewDto.getUrl());
            Review.setImagePath(imagePath); // 이미지 경로 설정

            // 데이터베이스에 저장
            ReviewRepository.save(Review);

        } catch (Exception e) {
            // 예외 발생 시 로그 기록 및 사용자에게 전달할 메시지 생성
            e.printStackTrace();
            throw new RuntimeException("리뷰 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    //리뷰 가져오기
    public List<ReviewDto> getAllReview(){
    	return null;
    }
}
