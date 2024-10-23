package com.yejo.interior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yejo.interior.service.CompanyLocationService;
import com.yejo.interior.service.ReviewService;


@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private ReviewService reviewService;
	@Autowired
	private CompanyLocationService locationService;
	
	@GetMapping("/banner")
	public String bannerPage() {
		return "admin/banner";
	}
	
	@GetMapping("/estimate")
	public String estimatePage() {
		return "admin/estimate";
	}
	
	@GetMapping("/about")
	public String about() {
		return "admin/about";
	}
	
	@GetMapping("/portfolio")
	public String portfolio() {
		return "admin/portfolio";
	}
	
	@GetMapping("/location")
	public String location() {
		return "admin/location";
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
	
	@GetMapping("/Review-management")
	public String ReviewManagementPage(Model model) {
		model.getAttribute("도레미");
		return "admin/Review-management";
	}
	
	
}
