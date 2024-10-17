package com.yejo.interior.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {


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
	
	@GetMapping("/review-management")
	public String reviewManagementPage() {
		return "admin/review-management";
	}
	
	
}
