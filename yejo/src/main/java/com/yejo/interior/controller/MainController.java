package com.yejo.interior.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String main() {
		return "main/main";
	}
	
	@GetMapping("company")
	public String company() {
		return "main/company";
	}
	
	@GetMapping("review")
	public String review() {
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
	public String location() {
		return "main/location";
	}
	
	@GetMapping("portfolio-detail")
	public String portfolio_detail() {
		return "main/portfolio-detail";
	}
}
