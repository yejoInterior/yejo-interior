package com.yejo.interior.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yejo.interior.service.PortfolioService;

@Controller
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;
    
    private static final Logger logger = LoggerFactory.getLogger(PortfolioService.class);

    @PostMapping("/save")
    @ResponseBody  // JSON 반환을 위해 추가
    public Map<String, Object> savePortfolio(String title, String location, Double area, String duration, String type,
                                             MultipartFile thumbnailImage, List<MultipartFile> portfolioImages) {
        Map<String, Object> response = new HashMap<>();

        try {
            portfolioService.savePortfolio(title, location, area, duration, type, thumbnailImage, portfolioImages);
            response.put("success", true);
            response.put("message", "포트폴리오가 성공적으로 저장되었습니다.");
        } catch (IOException e) {
            logger.error("파일 업로드 중에 문제가 발생했어요 ㄷㄷ", e);
            response.put("success", false);
            response.put("message", "파일 업로드 중 오류가 발생했습니다.");
        }

        return response;  // JSON 형식으로 반환
    }
    
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Map<String, Object> deletePortfolio(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            portfolioService.deletePortfolio(id);
            response.put("success", true);
            response.put("message", "포트폴리오가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            logger.error("포트폴리오 삭제에 문제가 발생했네요 ㄷㄷ", e);
            response.put("success", false);
            response.put("message", "삭제 중 오류가 발생했습니다.");
        }
        return response;
    }

}
