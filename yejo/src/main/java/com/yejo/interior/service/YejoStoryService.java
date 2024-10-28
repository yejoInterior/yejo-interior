package com.yejo.interior.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yejo.interior.entity.YejoStoryEntity;
import com.yejo.interior.repository.YejoStoryRepository;

@Service
public class YejoStoryService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private YejoStoryRepository yejoStoryRepository;

    public YejoStoryEntity getIntroduction() {
        return yejoStoryRepository.findFirstByOrderByIdDesc();
    }

    public YejoStoryEntity saveIntroduction(String text, MultipartFile image) throws IOException {
        YejoStoryEntity newStory = new YejoStoryEntity();
        newStory.setIntroductionText(text);

        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(image);
            newStory.setImagePath(imagePath);
        }

        return yejoStoryRepository.save(newStory);
    }

    public YejoStoryEntity updateIntroduction(YejoStoryEntity existingStory, String text, MultipartFile image) throws IOException {
        existingStory.setIntroductionText(text);

        // 이미지가 새로 업로드된 경우
        if (image != null && !image.isEmpty()) {
            // 기존 이미지를 삭제
            if (existingStory.getImagePath() != null) {
                deleteExistingImage(existingStory.getImagePath());
            }

            // 새 이미지 저장
            String newImagePath = saveImage(image);
            existingStory.setImagePath(newImagePath);
        }

        return yejoStoryRepository.save(existingStory);
    }

    // 이미지 저장 로직
    private String saveImage(MultipartFile imageFile) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path path = Paths.get(uploadPath, fileName);
        Files.createDirectories(path.getParent());
        Files.copy(imageFile.getInputStream(), path);
        return fileName;
    }

    // 기존 이미지 삭제 로직
    private void deleteExistingImage(String imagePath) throws IOException {
        Path path = Paths.get(uploadPath, imagePath);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}

