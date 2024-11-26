package com.yejo.interior.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yejo.interior.entity.YejoStoryEntity;
import com.yejo.interior.repository.YejoStoryRepository;
import com.yejo.interior.utility.FileUtility;

@Service
public class YejoStoryService {

    @Autowired
    private YejoStoryRepository yejoStoryRepository;

    @Autowired
    private FileUtility fileUtility; // FileUtility 주입

    @Value("${cdn.path}")
    private String cdnPath;
    
    
    public YejoStoryEntity getIntroduction() {
        return yejoStoryRepository.findFirstByOrderByIdDesc();
    }
    
    // 소개글 저장
    public YejoStoryEntity saveIntroduction(String text, MultipartFile image) throws IOException {
        YejoStoryEntity newStory = new YejoStoryEntity();
        newStory.setIntroductionText(text);

        // 이미지가 있으면 파일 업로드 처리
        if (image != null && !image.isEmpty()) {
            Map<String, Object> uploadResult = fileUtility.uploadFile(image, "main");
            if ((Boolean) uploadResult.get("success")) {
                String imagePath = (String) uploadResult.get("filePath");
                newStory.setImagePath(imagePath);
            }
        }

        return yejoStoryRepository.save(newStory);
    }

    // 소개글 업데이트
    public YejoStoryEntity updateIntroduction(YejoStoryEntity existingStory, String text, MultipartFile image) throws IOException {
        existingStory.setIntroductionText(text);

        // 이미지가 새로 업로드된 경우
        if (image != null && !image.isEmpty()) {
            // 기존 이미지를 삭제할 필요가 있으면 추가 로직 구현

            // 새 이미지 업로드
            Map<String, Object> uploadResult = fileUtility.uploadFile(image, "main");
            if ((Boolean) uploadResult.get("success")) {
                String newImagePath = (String) uploadResult.get("filePath");
                existingStory.setImagePath(newImagePath);
            }
        }

        return yejoStoryRepository.save(existingStory);
    }
}
