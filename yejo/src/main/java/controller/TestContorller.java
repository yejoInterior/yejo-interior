package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestContorller {

	@GetMapping("/admin")
	public String adminIndexPage() {
		return "admin/index";
	}
}
