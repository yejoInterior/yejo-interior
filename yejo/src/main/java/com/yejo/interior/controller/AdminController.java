package com.yejo.interior.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yejo.interior.entity.BannerEntity;
import com.yejo.interior.entity.CompanyLocationEntity;
import com.yejo.interior.entity.PortfolioEntity;
import com.yejo.interior.entity.Review;
import com.yejo.interior.entity.YejoStoryEntity;
import com.yejo.interior.service.BannerService;
import com.yejo.interior.service.CompanyLocationService;
import com.yejo.interior.service.PortfolioService;
import com.yejo.interior.service.ReviewService;
import com.yejo.interior.service.YejoStoryService;


@Controller
@RequestMapping("admin_of_yejo")
public class AdminController {

	@Autowired
	private ReviewService reviewService;
	@Autowired
	private CompanyLocationService locationService;
	@Autowired
	private YejoStoryService yejoStoryService;
	@Autowired
	private BannerService bannerService;
	@Autowired
	private PortfolioService portfolioService;
	
	@GetMapping("/")
	public String main() {
		return "admin/main";
	}
	
	@GetMapping("/banner")
	public String bannerPage(Model model) {
		List<BannerEntity> bannerList = bannerService.getBanner();
		model.addAttribute("bannerList",bannerList);
		return "admin/banner";
	}
	
	@GetMapping("/estimate")
	public String estimatePage() {
		return "admin/estimate";
	}
	
	@GetMapping("/about")
	public String about(Model model) {
		YejoStoryEntity yejoStory=yejoStoryService.getIntroduction();
        model.addAttribute("savedIntroductionText", yejoStory.getIntroductionText());
        model.addAttribute("savedImagePath", yejoStory.getImagePath());
		return "admin/about";
	}
	
    @GetMapping("/portfolio")
    public String getPortfolioList(Model model) {
        List<PortfolioEntity> portfolioList = portfolioService.getAllPortfolios();
        model.addAttribute("portfolioList", portfolioList);
        return "admin/portfolio"; // 뷰 이름
    }
	
	@GetMapping("/location")
	public String showLocationPage(Model model) {
	    CompanyLocationEntity location = locationService.getLocation();
	    model.addAttribute("location", location);
	    return "admin/location"; // 템플릿 경로
	}

	
	@GetMapping("/utilities-animation")
	public String utilitiesAnimationPage() {
		return"admin/utilities-animation";
	}
	
	@GetMapping("/utilities-border")
	public String utilitiesBorderPage() {
		return"admin/utilities-border";
	}
	
	@GetMapping("/utilities-color")
	public String utilitiesColorPage() {
		return"admin/utilities-color";
	}
	
	@GetMapping("/utilities-other")
	public String utilitiesOtherPage() {
		return"admin/utilities-other";
	}
	
	@GetMapping("/company-info")
	public String companyInfoPage() {
		return "admin/company-info";
	}
	
	@GetMapping("/review-management")
	public String reviewManagementPage(Model model) {
		List<Review> reviews = reviewService.getAllReview();
		model.addAttribute("reviews",reviews);
		return "admin/review-management";
	}
	
	
}
