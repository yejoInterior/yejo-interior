package com.yejo.interior.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.yejo.interior.entity.CompanyLocationEntity;
import com.yejo.interior.entity.Review;
import com.yejo.interior.entity.YejoStoryEntity;
import com.yejo.interior.service.BannerService;
import com.yejo.interior.service.CompanyLocationService;
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
	private BannerService bannerService;
	
	@GetMapping("/")
	public String main(Model model) {
		List<String> bannerPath = bannerService.getBannerPath();
		model.addAttribute("bannerPath",bannerPath);
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
	
	@GetMapping("portfolio")
	public String portfolio() {
		return "main/portfolio";
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
	
	@GetMapping("portfolio-detail")
	public String portfolio_detail() {
		return "main/portfolio-detail";
	}
}
