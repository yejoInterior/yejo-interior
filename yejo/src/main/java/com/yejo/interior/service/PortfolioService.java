package com.yejo.interior.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yejo.interior.entity.PortfolioEntity;
import com.yejo.interior.entity.PortfolioImageEntity;
import com.yejo.interior.repository.PortfolioImageRepository;
import com.yejo.interior.repository.PortfolioRepository;
import com.yejo.interior.utility.FileUtility;

@Service
public class PortfolioService {

    private static final Logger logger = LoggerFactory.getLogger(PortfolioService.class);

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioImageRepository portfolioImageRepository;

    @Autowired
    private FileUtility fileUtility; // FileUtility 주입

    @Value("${cdn.path}")
    private String cdnPath;

    @Transactional
    public PortfolioEntity savePortfolio(String title, String location, Double area, String duration, String type, MultipartFile image, List<MultipartFile> portfolioImages) throws IOException {
        // 썸네일 이미지 업로드
        Map<String, Object> imageUploadResult = fileUtility.uploadFile(image, "portfolio");
        String imagePath = (Boolean) imageUploadResult.get("success") ? (String) imageUploadResult.get("filePath") : null;

        PortfolioEntity portfolio = new PortfolioEntity();
        portfolio.setTitle(title);
        portfolio.setLocation(location);
        portfolio.setArea(area);
        portfolio.setDuration(duration);
        portfolio.setType(type);
        portfolio.setThumbnailImage(imagePath); // 썸네일 이미지 경로 저장

        portfolio = portfolioRepository.save(portfolio);

        List<PortfolioImageEntity> imageEntities = new ArrayList<>();
        for (MultipartFile portfolioImage : portfolioImages) {
            // 각 포트폴리오 이미지를 FTP에 업로드
            Map<String, Object> portfolioImageUploadResult = fileUtility.uploadFile(portfolioImage, "portfolio");
            String portfolioImagePath = (Boolean) portfolioImageUploadResult.get("success") ? (String) portfolioImageUploadResult.get("filePath") : null;

            if (portfolioImagePath != null) {
                PortfolioImageEntity imageEntity = new PortfolioImageEntity();
                imageEntity.setImagePath(portfolioImagePath);
                imageEntity.setPortfolio(portfolio);
                imageEntities.add(imageEntity);
            }
        }

        portfolioImageRepository.saveAll(imageEntities);

        return portfolio;
    }

    public List<PortfolioEntity> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    public void deletePortfolio(Long id) {
        PortfolioEntity portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("포트폴리오를 찾을 수 없습니다."));

        // 썸네일 이미지 삭제
        deleteFile(portfolio.getThumbnailImage());

        // 포트폴리오 이미지 파일들 삭제
        for (PortfolioImageEntity image : portfolio.getImages()) {
            deleteFile(image.getImagePath());
        }

        portfolioRepository.deleteById(id);
    }

    // FTP 서버에서 파일 삭제 메서드
    private void deleteFile(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            logger.info("Trying to delete file from CDN at: " + filePath);
            // CDN 경로에서 파일 삭제
            fileUtility.deleteFileFromCDN(filePath);
        } else {
            logger.warn("파일 경로가 null이거나 빈 값입니다.");
        }
    }

    public PortfolioEntity getPortfolioById(Long id) {
        return portfolioRepository.findById(id).orElse(null);
    }

    public Page<PortfolioEntity> getPortfoliosWithPagination(Pageable pageable) {
        return portfolioRepository.findAll(pageable);
    }
}
