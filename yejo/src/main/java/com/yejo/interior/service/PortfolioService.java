package com.yejo.interior.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yejo.interior.entity.PortfolioEntity;
import com.yejo.interior.entity.PortfolioImageEntity;
import com.yejo.interior.repository.PortfolioImageRepository;
import com.yejo.interior.repository.PortfolioRepository;

@Service
public class PortfolioService {

    private static final Logger logger = LoggerFactory.getLogger(PortfolioService.class);

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioImageRepository portfolioImageRepository;

    @Value("${upload.path}")
    private String uploadPath;  // 절대 경로로 설정

    @Transactional
    public PortfolioEntity savePortfolio(String title, String location, Double area, String duration, String type, MultipartFile image, List<MultipartFile> portfolioImages) throws IOException {
        String imagePath = saveFile(image);  // 크롭된 이미지 또는 원본 이미지 처리

        PortfolioEntity portfolio = new PortfolioEntity();
        portfolio.setTitle(title);
        portfolio.setLocation(location);
        portfolio.setArea(area);
        portfolio.setDuration(duration);
        portfolio.setType(type);
        portfolio.setThumbnailImage(imagePath);  // DB에 상대 경로만 저장

        portfolio = portfolioRepository.save(portfolio);

        List<PortfolioImageEntity> imageEntities = new ArrayList<>();
        for (MultipartFile imageFile : portfolioImages) {
            String portfolioImagePath = saveFile(imageFile);
            PortfolioImageEntity imageEntity = new PortfolioImageEntity();
            imageEntity.setImagePath(portfolioImagePath);
            imageEntity.setPortfolio(portfolio);
            imageEntities.add(imageEntity);
        }

        portfolioImageRepository.saveAll(imageEntities);

        return portfolio;
    }

    // 파일 저장 메서드
    private String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            logger.warn("Received empty file, skipping.");
            return null;
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadPath, fileName);
        Files.createDirectories(path.getParent()); // 경로 생성
        Files.copy(file.getInputStream(), path);  // 파일 저장
        
        logger.info("File saved successfully at path: " + path.toAbsolutePath());

        return "uploads/" + fileName;  // DB에 상대 경로 저장
    }

    public List<PortfolioEntity> getAllPortfolios() {
        return portfolioRepository.findAll();
    }
    
    public void deletePortfolio(Long id) {
        PortfolioEntity portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("포트폴리오를 찾을 수 없습니다."));

        // 썸네일 파일 삭제
        deleteFile(portfolio.getThumbnailImage());

        // 포트폴리오 이미지 파일들 삭제
        for (PortfolioImageEntity image : portfolio.getImages()) {
            deleteFile(image.getImagePath());
        }

        // 데이터베이스에서 포트폴리오 삭제 (이미지 엔티티는 cascade로 삭제됨)
        portfolioRepository.deleteById(id);
    }

    // 파일 삭제 메서드
    private void deleteFile(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            // 파일 경로를 절대 경로로 변환
            File file = new File(uploadPath + File.separator + filePath.replace("uploads/", ""));
            logger.info("Trying to delete file at: " + file.getAbsolutePath());

            if (file.exists()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    logger.error("파일 삭제에 실패했습니다: " + file.getAbsolutePath());
                } else {
                    logger.info("File deleted successfully: " + file.getAbsolutePath());
                }
            } else {
                logger.warn("파일을 찾을 수 없습니다: " + file.getAbsolutePath());
            }
        } else {
            logger.warn("파일 경로가 null이거나 빈 값입니다.");
        }
    }
    
    public PortfolioEntity getPortfolioById(Long id) {
        return portfolioRepository.findById(id).orElse(null);
    }
    
}
