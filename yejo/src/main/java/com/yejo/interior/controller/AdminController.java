package com.yejo.interior.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.yejo.interior.entity.BannerEntity;
import com.yejo.interior.entity.CompanyLocationEntity;
import com.yejo.interior.entity.ConsultantEntity;
import com.yejo.interior.entity.PopupEntity;
import com.yejo.interior.entity.PortfolioEntity;
import com.yejo.interior.entity.Review;
import com.yejo.interior.entity.YejoStoryEntity;
import com.yejo.interior.service.BannerService;
import com.yejo.interior.service.CompanyLocationService;
import com.yejo.interior.service.ConsultantService;
import com.yejo.interior.service.KakaoTalkService;
import com.yejo.interior.service.PopupService;
import com.yejo.interior.service.PortfolioService;
import com.yejo.interior.service.ReviewService;
import com.yejo.interior.service.YejoStoryService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("admin")
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
	@Autowired
	private ConsultantService consultantService;
	@Autowired
	private PopupService popupService;
	@Autowired
	private KakaoTalkService kakaoTalkService;
	
	@GetMapping("/")
	public String main(HttpSession session) {
		session.invalidate();
		return "admin/main";
	}
	
	@GetMapping("/banner")
	public String bannerPage(Model model, HttpSession session) {
		Boolean checkIn = (Boolean) session.getAttribute("checkIn");
        if (checkIn != null && checkIn) {
        	List<BannerEntity> bannerList = bannerService.getBanner();
    		model.addAttribute("bannerList",bannerList);
    		return "admin/banner";
        } else {
            return "redirect:/admin/";  // 로그인 안 된 경우 로그인 페이지로 리다이렉트
        }
		
	}
	
	@GetMapping("/estimate")
	public String estimatePage(Model model, HttpSession session) {
		Boolean checkIn = (Boolean) session.getAttribute("checkIn");
        if (checkIn != null && checkIn) {
        	List<ConsultantEntity> estimateList = consultantService.getAllEstimate();
    		model.addAttribute("estimateList",estimateList);
    		return "admin/estimate";
        } else {
            return "redirect:/admin/";  // 로그인 안 된 경우 로그인 페이지로 리다이렉트
        }
		
	}
	
	@GetMapping("/about")
	public String about(Model model, HttpSession session) {
		Boolean checkIn = (Boolean) session.getAttribute("checkIn");
        if (checkIn != null && checkIn) {
        	YejoStoryEntity yejoStory=yejoStoryService.getIntroduction();
            model.addAttribute("savedIntroductionText", yejoStory.getIntroductionText());
            model.addAttribute("savedImagePath", yejoStory.getImagePath());
    		return "admin/about";
        } else {
            return "redirect:/admin/";  // 로그인 안 된 경우 로그인 페이지로 리다이렉트
        }
		
	}
	
    @GetMapping("/portfolio")
    public String getPortfolioList(Model model, HttpSession session) {
    	Boolean checkIn = (Boolean) session.getAttribute("checkIn");
        if (checkIn != null && checkIn) {
        	List<PortfolioEntity> portfolioList = portfolioService.getAllPortfolios();
            model.addAttribute("portfolioList", portfolioList);
            return "admin/portfolio"; // 뷰 이름
        } else {
            return "redirect:/admin/";  // 로그인 안 된 경우 로그인 페이지로 리다이렉트
        }
        
    }
	
	@GetMapping("/location")
	public String showLocationPage(Model model, HttpSession session) {
		Boolean checkIn = (Boolean) session.getAttribute("checkIn");
        if (checkIn != null && checkIn) {
        	CompanyLocationEntity location = locationService.getLocation();
    	    model.addAttribute("location", location);
    	    return "admin/location"; // 템플릿 경로
        } else {
            return "redirect:/admin/";  // 로그인 안 된 경우 로그인 페이지로 리다이렉트
        }
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
	public String reviewManagementPage(Model model, HttpSession session) {
		Boolean checkIn = (Boolean) session.getAttribute("checkIn");
        if (checkIn != null && checkIn) {
        	List<Review> reviews = reviewService.getAllReview();
    		model.addAttribute("reviews",reviews);
    		return "admin/review-management";
        } else {
            return "redirect:/admin/";  // 로그인 안 된 경우 로그인 페이지로 리다이렉트
        }
		
	}
	
	@GetMapping("/popup")
	public String popupPage(Model model, HttpSession session) {
		Boolean checkIn = (Boolean) session.getAttribute("checkIn");
        if (checkIn != null && checkIn) {
        	Optional<PopupEntity> popupOptional = popupService.getPopup();
    		PopupEntity popup = popupOptional.orElse(null); // Optional 해제
    	    model.addAttribute("popup", popup);
    		return "admin/popup";
        } else {
            return "redirect:/admin/";  // 로그인 안 된 경우 로그인 페이지로 리다이렉트
        }
	}
	
	@Value("${kakao.restApiKey}")
	private String REST_API_KEY;
	@Value("${kakao.redirectUri}")
	private String REDIRECT_URI;
	@GetMapping("/kakao")
	public String kakaoAuthPage() {
		 // 카카오 로그인 페이지 URL로 리디렉션
        String authUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + REST_API_KEY
                + "&redirect_uri=" + REDIRECT_URI + "&response_type=code";
        
        return "redirect:" + authUrl; 
    }
	
	@GetMapping("/oauth") //로컬에서 하려면 code 복사해서 postMan으로 테스트 해야합니다~
	public String kakaoCallback(@RequestParam("code") String authorizationCode) {
	    System.out.println("Authorization Code: " + authorizationCode);
	    kakaoTalkService.createToken(authorizationCode);
	    return "admin/banner";  
	}
	
    // 로그인 처리 POST 요청
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody Map<String, String> requestData, HttpSession session) {
	    String password = requestData.get("password");  // 클라이언트에서 전송한 비밀번호 받기
	    if ("yejo7048@".equals(password)) {  // 비밀번호가 맞으면
	        session.setAttribute("checkIn", true);  // 세션에 로그인 정보 저장
	        return ResponseEntity.ok().body("{\"success\": true}");  // 로그인 성공 응답
	    } else {
	        return ResponseEntity.ok().body("{\"success\": false}");  // 로그인 실패 응답
	    }
	}


	
}
