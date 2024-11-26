/*package com.yejo.interior.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.yejo.interior.entity.PortfolioEntity;
import com.yejo.interior.entity.PortfolioImageEntity;
import com.yejo.interior.repository.PortfolioImageRepository;
import com.yejo.interior.repository.PortfolioRepository;
import com.yejo.interior.utility.FileUtility;

class PortfolioServiceTest {

    @InjectMocks
    private PortfolioService portfolioService;

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private PortfolioImageRepository portfolioImageRepository;

    @Mock
    private FileUtility fileUtility;

    @Mock
    private MultipartFile imageFile;

    @Mock
    private List<MultipartFile> portfolioImages;

    private PortfolioEntity portfolio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        portfolio = new PortfolioEntity();
        portfolio.setId(1L);
        portfolio.setTitle("Test Portfolio");
    }

    @Test
    void testSavePortfolio() throws IOException {
        Map<String, Object> uploadResult = new HashMap<>();
        uploadResult.put("success", true);
        uploadResult.put("filePath", "http://cdn.path/portfolio/thumbnail.jpg");

        when(fileUtility.uploadFile(any(MultipartFile.class), any(String.class))).thenReturn(uploadResult);
        when(portfolioRepository.save(any(PortfolioEntity.class))).thenReturn(portfolio);

        PortfolioEntity savedPortfolio = portfolioService.savePortfolio("Title", "Location", 100.0, "Duration", "Type", imageFile, portfolioImages);

        assertNotNull(savedPortfolio);
        verify(portfolioRepository).save(any(PortfolioEntity.class));
        verify(fileUtility).uploadFile(imageFile, "portfolio");
    }

    @Test
    void testDeletePortfolio() {
        portfolio.setThumbnailImage("http://cdn.path/portfolio/thumbnail.jpg");
        
        PortfolioImageEntity imageEntity = new PortfolioImageEntity();
        imageEntity.setImagePath("http://cdn.path/portfolio/image1.jpg");
        portfolio.setImages(List.of(imageEntity));

        when(portfolioRepository.findById(1L)).thenReturn(java.util.Optional.of(portfolio));

        portfolioService.deletePortfolio(1L);

        verify(fileUtility).deleteFileFromCDN("http://cdn.path/portfolio/thumbnail.jpg");
        verify(fileUtility).deleteFileFromCDN("http://cdn.path/portfolio/image1.jpg");
        verify(portfolioRepository).deleteById(1L);
    }
}

*/