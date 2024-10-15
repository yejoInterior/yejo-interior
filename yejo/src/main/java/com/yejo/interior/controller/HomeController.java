package com.yejo.interior.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/admin")
	public String adminIndexPage() {
		return "admin/index";
	}
	
	@GetMapping("/about")
	public String about() {
		return "admin/about";
	}
	
	@GetMapping("portfolio")
	public String portfolio() {
		return "admin/portfolio";
	}
	
	@GetMapping("/location")
	public String location() {
		return "admin/location";
	}
}
