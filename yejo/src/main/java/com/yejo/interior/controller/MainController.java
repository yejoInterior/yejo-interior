package com.yejo.interior.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.yejo.interior.entity.CompanyLocationEntity;
import com.yejo.interior.entity.PortfolioEntity;
import com.yejo.interior.entity.Review;
import com.yejo.interior.entity.YejoStoryEntity;
import com.yejo.interior.service.CompanyLocationService;
import com.yejo.interior.service.PortfolioService;
import com.yejo.interior.service.ReviewService;
import com.yejo.interior.service.YejoStoryService;

@Controller
public class MainController {

	@Autowired
	private ReviewService reviewService;
	@Autowired
	private CompanyLocationService locationService;
	@Autowired
	private YejoStoryService yejoStoryService;
	@Autowired
	private PortfolioService portfolioService;
	
	@GetMapping("/")
	public String main() {
		return "main/main";
	}
	
	@GetMapping("company")
	public String company(Model model) {
		YejoStoryEntity yejoStory=yejoStoryService.getIntroduction();
        model.addAttribute("savedIntroductionText", yejoStory.getIntroductionText());
        model.addAttribute("savedImagePath", yejoStory.getImagePath());
		return "main/company";
	}
	
	@GetMapping("review")
	public String review(Model model) {
		List<Review> reviews = reviewService.getAllReview();
		
		for (Review review : reviews) {
	        List<String> tags = Arrays.asList(review.getTag().split(","));
	        review.setTagList(tags); // Review 클래스에 tagList 필드 추가 필요
	    }
		
		model.addAttribute("reviews", reviews);
		return "main/review";
	}

	@GetMapping("consulting")
	public String consultant() {
		return "main/consultant";
	}

	@GetMapping("location")
	public String location(Model model) {
        CompanyLocationEntity location = locationService.getLocation();
        model.addAttribute("location", location);
        return "main/location";
	}
	
	@GetMapping("portfolio")
	public String getPortfolioList(
			@RequestParam(value = "page", defaultValue = "0") int page,
			Model model) {
		int pageSize = 6; // 한 페이지에 보여줄 포트폴리오 개수
		
		Pageable pageable = PageRequest.of(page, pageSize); 
		Page<PortfolioEntity> portfolioPage = portfolioService.getPortfoliosWithPagination(pageable);
		
		model.addAttribute("portfolioPage", portfolioPage);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", portfolioPage.getTotalPages());
		
		return "main/portfolio"; // 뷰 이름
	}
	
	@GetMapping("portfolio-detail/{id}")
	public String portfolioDetail(@PathVariable Long id, Model model) {
	    // id에 해당하는 포트폴리오를 조회
	    PortfolioEntity portfolio = portfolioService.getPortfolioById(id);
	    
	    if (portfolio == null) {
	        // 포트폴리오가 없을 경우 에러 페이지로 리다이렉트하거나 메시지를 보여줄 수 있습니다.
	        return "redirect:/portfolio";  // 포트폴리오 목록 페이지로 리다이렉트
	    }
	    
	    // 포트폴리오 정보를 모델에 추가하여 뷰로 전달
	    model.addAttribute("portfolio", portfolio);
	    return "main/portfolio-detail";
	}
}
