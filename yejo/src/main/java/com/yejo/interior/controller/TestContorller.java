package com.yejo.interior.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestContorller {

	@GetMapping("/")
	public String adminIndexPage() {
		return "index";
	}
}
