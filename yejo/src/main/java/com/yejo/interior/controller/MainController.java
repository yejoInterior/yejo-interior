package com.yejo.interior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yejo.interior.entity.CompanyLocationEntity;
import com.yejo.interior.service.CompanyLocationService;

@Controller
public class MainController {

	@Autowired
	CompanyLocationService locationService;
	
	@GetMapping("/")
	public String main() {
		return "main/main";
	}
	
	@GetMapping("company")
	public String company() {
		return "main/company";
	}
	
	@GetMapping("Review")
	public String Review() {
		return "main/Review";
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
