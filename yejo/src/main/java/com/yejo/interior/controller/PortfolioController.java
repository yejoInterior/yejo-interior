package com.yejo.interior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.yejo.interior.service.PortfolioService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping("/save")
    public String savePortfolio(String title, String location, Double area, String duration, String type,
                                MultipartFile thumbnailImage, List<MultipartFile> portfolioImages, Model model) {

        try {
            portfolioService.savePortfolio(title, location, area, duration, type, thumbnailImage, portfolioImages);
            model.addAttribute("successMessage", "포트폴리오가 성공적으로 저장되었습니다.");
        } catch (IOException e) {
            model.addAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
        }

        return "redirect:/admin/portfolio";
    }
}
