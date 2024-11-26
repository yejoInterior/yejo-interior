/*package com.yejo.interior.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.yejo.interior.entity.YejoStoryEntity;
import com.yejo.interior.repository.YejoStoryRepository;
import com.yejo.interior.utility.FileUtility;

class YejoStoryServiceTest {

    @InjectMocks
    private YejoStoryService yejoStoryService;

    @Mock
    private YejoStoryRepository yejoStoryRepository;

    @Mock
    private FileUtility fileUtility;

    @Mock
    private MultipartFile imageFile;

    private YejoStoryEntity story;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        story = new YejoStoryEntity();
        story.setId(1L);
        story.setIntroductionText("Old Introduction");
        story.setImagePath("old_image.jpg");
    }

    @Test
    void testSaveIntroductionWithImage() throws IOException {
        Map<String, Object> uploadResult = new HashMap<>();
        uploadResult.put("success", true);
        uploadResult.put("filePath", "http://cdn.path/main/new_image.jpg");

        when(fileUtility.uploadFile(eq(imageFile), eq("main"))).thenReturn(uploadResult);
        when(yejoStoryRepository.save(any(YejoStoryEntity.class))).thenReturn(story);

        YejoStoryEntity newStory = yejoStoryService.saveIntroduction("New Introduction", imageFile);

        assertNotNull(newStory);
        assertEquals("New Introduction", newStory.getIntroductionText());
        assertEquals("http://cdn.path/main/new_image.jpg", newStory.getImagePath());
        verify(yejoStoryRepository).save(any(YejoStoryEntity.class));
    }

    @Test
    void testUpdateIntroductionWithNewImage() throws IOException {
        Map<String, Object> uploadResult = new HashMap<>();
        uploadResult.put("success", true);
        uploadResult.put("filePath", "http://cdn.path/main/updated_image.jpg");

        when(fileUtility.uploadFile(eq(imageFile), eq("main"))).thenReturn(uploadResult);
        when(yejoStoryRepository.save(any(YejoStoryEntity.class))).thenReturn(story);

        YejoStoryEntity updatedStory = yejoStoryService.updateIntroduction(story, "Updated Introduction", imageFile);

        assertNotNull(updatedStory);
        assertEquals("Updated Introduction", updatedStory.getIntroductionText());
        assertEquals("http://cdn.path/main/updated_image.jpg", updatedStory.getImagePath());
        verify(yejoStoryRepository).save(any(YejoStoryEntity.class));
    }

    @Test
    void testUpdateIntroductionWithoutImage() throws IOException {
        when(yejoStoryRepository.save(any(YejoStoryEntity.class))).thenReturn(story);

        YejoStoryEntity updatedStory = yejoStoryService.updateIntroduction(story, "Updated Introduction", null);

        assertNotNull(updatedStory);
        assertEquals("Updated Introduction", updatedStory.getIntroductionText());
        assertEquals("old_image.jpg", updatedStory.getImagePath()); // 기존 이미지 경로 유지
        verify(yejoStoryRepository).save(any(YejoStoryEntity.class));
    }
}
*/