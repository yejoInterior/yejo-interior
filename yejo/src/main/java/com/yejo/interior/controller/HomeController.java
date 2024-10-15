package com.yejo.interior.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/admin")
	public String adminIndexPage() {
		return "admin/index";
	}
	
	@GetMapping("/tables")
	public String adminTablesPage() {
		return "admin/tables";
	}
	
	@GetMapping("utilities-animation")
	public String utilitiesAnimationPage() {
		return"admin/utilities-animation";
	}
	
	@GetMapping("utilities-border")
	public String utilitiesBorderPage() {
		return"admin/utilities-border";
	}
	
	@GetMapping("utilities-color")
	public String utilitiesColorPage() {
		return"admin/utilities-color";
	}
	
	@GetMapping("utilities-other")
	public String utilitiesOtherPage() {
		return"admin/utilities-other";
	}
	
	@GetMapping("company-info")
	public String companyInfoPage() {
		return "admin/company-info";
	}
	
	@GetMapping("review-management")
	public String reviewManagementPage() {
		return "admin/review-management";
	}
}
