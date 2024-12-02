package com.yejo.interior.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import com.yejo.interior.repository.ConsultantRepository;
import com.yejo.interior.service.BannerService;
import com.yejo.interior.service.CompanyLocationService;
import com.yejo.interior.service.ConsultantService;
import com.yejo.interior.service.KakaoTalkService;
import com.yejo.interior.service.PolicyService;
import com.yejo.interior.service.PopupService;
import com.yejo.interior.service.PortfolioService;
import com.yejo.interior.service.AdminPasswordService;
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
	@Autowired
	private PolicyService  policyService;
	@Autowired
	private AdminPasswordService adminPasswordService;
	@Autowired
	private ConsultantRepository consultantRepository;
	
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
	
	@GetMapping("/policy")
	public String policyPage(Model model, HttpSession session) {
		Boolean checkIn = (Boolean) session.getAttribute("checkIn");
        if (checkIn != null && checkIn) {
        	System.out.println(policyService.getPolicyContent());
        	model.addAttribute("content", policyService.getPolicyContent());
    		return "admin/policy";
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
	    kakaoTalkService.createToken(authorizationCode);
	    return "admin/banner";  
	}
	
    // 로그인 처리 POST 요청
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String, String> requestData, HttpSession session) {
        String password = requestData.get("password");  // 클라이언트에서 전송한 비밀번호 받기
        
        // DB에서 패스워드와 비교
        boolean isValid = adminPasswordService.checkPassword(password);
        
        if (isValid) {  // 비밀번호가 맞으면
            session.setAttribute("checkIn", true);  // 세션에 로그인 정보 저장
            return ResponseEntity.ok().body("{\"success\": true}");  // 로그인 성공 응답
        } else {
            return ResponseEntity.ok().body("{\"success\": false}");  // 로그인 실패 응답
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return ResponseEntity.status(HttpStatus.FOUND)
                             .location(URI.create("/admin/")) // 리다이렉트 URL
                             .build();
    }

	@PostMapping("/changePassword")
	@ResponseBody
	public ResponseEntity<?> changePwd(@RequestBody Map<String, String> request) {
	    try {
	        String newPassword = request.get("password"); // 클라이언트에서 보낸 비밀번호

	        String result = adminPasswordService.changePassword(newPassword);

	        return ResponseEntity.ok(result);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("false");
	    }
	}
	
	@GetMapping("/inflow-stats")
	@ResponseBody
	public Map<String, Integer> getInflowStats() {
	    List<ConsultantEntity> consultants = consultantRepository.findAll();
	    Map<String, Integer> inflowStats = new HashMap<>();

	    for (ConsultantEntity consultant : consultants) {
	        String inflow = consultant.getInflow();
	        inflowStats.put(inflow, inflowStats.getOrDefault(inflow, 0) + 1);
	    }
	    return inflowStats;
	}

	
}
