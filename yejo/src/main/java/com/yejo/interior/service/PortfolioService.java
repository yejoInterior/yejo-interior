package com.yejo.interior.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yejo.interior.entity.PortfolioEntity;
import com.yejo.interior.entity.PortfolioImageEntity;
import com.yejo.interior.repository.PortfolioImageRepository;
import com.yejo.interior.repository.PortfolioRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioImageRepository portfolioImageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Transactional
    public PortfolioEntity savePortfolio(String title, String location, Double area, String duration, String type, MultipartFile thumbnailImage, List<MultipartFile> portfolioImages) throws IOException {

        // 썸네일 이미지 저장
        String thumbnailPath = saveFile(thumbnailImage);
        
        // 포트폴리오 엔티티 생성
        PortfolioEntity portfolio = new PortfolioEntity();
        portfolio.setTitle(title);
        portfolio.setLocation(location);
        portfolio.setArea(area);
        portfolio.setDuration(duration);
        portfolio.setType(type);
        portfolio.setThumbnailImage(thumbnailPath);

        portfolio = portfolioRepository.save(portfolio);

        // 포트폴리오 이미지 저장
        List<PortfolioImageEntity> imageEntities = new ArrayList<>();
        for (MultipartFile imageFile : portfolioImages) {
            String imagePath = saveFile(imageFile);
            PortfolioImageEntity imageEntity = new PortfolioImageEntity();
            imageEntity.setImagePath(imagePath);
            imageEntity.setPortfolio(portfolio);
            imageEntities.add(imageEntity);
        }

        portfolioImageRepository.saveAll(imageEntities);

        return portfolio;
    }

    // 파일 저장 메서드
    private String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        // 파일 이름 재정의
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File destinationFile = new File(uploadPath, fileName);
        
        file.transferTo(destinationFile); // 파일 저장
        
        return destinationFile.getPath();
    }
}

