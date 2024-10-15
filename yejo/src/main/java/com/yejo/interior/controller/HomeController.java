package com.yejo.interior.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/admin")
	public String adminIndexPage() {
		return "admin/index";
	}
	
	@GetMapping("/banner")
	public String bannerPage() {
		return "admin/bannerManagement";
	}
	
	@GetMapping("/estimate")
	public String estimatePage() {
		return "admin/estimate";
	}
}
