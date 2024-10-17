if(/iPhone|iPod|Android|iPad/.test(window.navigator.platform)){
	$(document)
	.on('focus', 'textarea,input,select', function(e) {
		$('#header').css('position', 'absolute');
	})
	.on('blur', 'textarea,input,select', function(e) {
		$('#header').css('position', '');
	});
}
$(document).ready(function(){
	stageResize();
	// 타이틀 변환
    var homeTile = document.title;  
    arrTitle = $('.sub-title h1').text();
    if($(".sub-title").length>0){
        document.title=arrTitle + " | " + homeTile;
    };

	// mobile navigation
	$(".nav-menu").html($("#gnb").html());
	$(".m-lang").html($(".lang").html());
	$(".btn-m-menu").click(function(e){
		e.preventDefault();
		if($("html").hasClass("menu-opened")){
			$("html").removeClass("menu-opened");
			
		}else{			
			$("html").addClass("menu-opened"); 
		}
	});


	$(".mobile-overlay, .close").click(function(){				
		$("html").removeClass("menu-opened");
	});

	// 메뉴
	$(".mobile-navigation nav > ul > li > a").click(function(){
		t = $(this).parent('li');
		if (t.hasClass('active')) {
			t.removeClass('active');
			t.find('.submenu').slideUp('fast');
			return false;
		}else {
			$(".mobile-navigation nav li").removeClass('active');
			t.addClass('active');
			if(t.find('div').hasClass('submenu')){
				$(".mobile-navigation nav .submenu").slideUp('fast');			
				t.find('.submenu').slideDown('fast');
				return false;
			}	
		}
	});
	//메인
	$('.main-visual').addClass("load");
	$('.main-visual .items').on('init', function(event, slick) {
    $('.main-visual .items').find('.slick-current').removeClass('slick-active').addClass('reset-animation');
    setTimeout( function() {
        $('.main-visual .items').find('.slick-current').removeClass('reset-animation').addClass('slick-active');
		}, 1);
	});
	$('.main-visual .items').on('init', function(event, slick) {
		$(".main-visual .pager-current").html('01');
		$(".main-visual .pager-all").html('0'+ slick.slideCount);
		$(".main-visual .progress").addClass("animate");
	}).slick({
		speed: 2000,
		autoplay:true,
		autoplaySpeed: 4000,
		slidesToShow: 1,
		slidesToScroll: 1,
		fade: true,
		lazyLoad:"progressive",
		arrows: true,
		appendArrows:'.mv-ctrl',
		dots:false,
		pauseOnFocus: false, 
		pauseOnHover: false
		
	}).on('beforeChange', function(event, slick, currentSlide, nextSlide){
		$(".main-visual .progress").removeClass("animate");		
	}).on('afterChange', function(event, slick, currentSlide){
		var current  = currentSlide +1;
		$(".main-visual .pager-current").html('0' + current);
		$(".main-visual .pager-all").html('0'+slick.slideCount);
		$(".main-visual .progress").addClass("animate");
	});

	//sub
	jQuery(".sub-visual").addClass("load");
	jQuery("#sub #header").addClass("header-white");

	$('.img-slider .items').slick({
	  speed: 800,
	  slidesToShow: 1,
	  slidesToScroll: 1,
	  fade: true,
	  arrows: true,
	  dots:true,
	  adaptiveHeight: true,
	  pauseOnHover: true,
		
	});

	// 텝
    $(".tab-content").hide();
    $("ul.tabs>li:first").addClass("active").attr('title','선택됨');
    $(".tab-content:first").show();
 
    $("ul.tabs>li").click(function(e) {
        e.preventDefault();
        $("ul.tabs>li").removeClass("active").removeAttr('title');
        $(this).addClass("active").attr('title','선택됨');
        $(".tab-content").hide();       
 
        var activeTab = $(this).find("a").attr("href");
        $(activeTab).fadeIn();
        return false;
    }); 

	//lnb
	if(jQuery(window).width() <= 640) {
		var sIdx = $(".lnb .swiper-slide.active").index();
		var swiper = new Swiper('.lnb', {
			slidesPerView: 'auto',
			preventClicks: false,
			initialSlide: sIdx
		});
		
	}
	
	$(window).bind("load scroll", function(){
		var headH = $("#header").height();
		if($(document).scrollTop() > headH){
			$("html").addClass("header-fixed");
		}else{
			$("html").removeClass("header-fixed");
		}
	});

	// 문서 클릭시 셀렉트박스 닫힘
	$(document).click(function(e){
		if(!$(event.target).closest(".site-link").length){
			$(".site-link").removeClass("active")
			$(".site-link .select-options").slideUp(100);
		};		
	});

	// fancybox

	$(".various").fancybox({
		padding     : 0,
		margin      : 10,
		transitionIn	: 'none',
		transitionOut	: 'none',
		type : 'iframe'
	});

	$(".pop_privacy").fancybox({
		padding     : 0,
		margin      : 10,
		fitToView	: false,
		closeClick	: false,
		openEffect	: 'none',
		closeEffect	: 'none',
		type		: 'ajax',
		helpers:  {
			overlay: {
				locked: false
			}
		}
	});

	$(".pop_email").fancybox({
		padding     : 0,
		margin      : 10,
		fitToView	: false,
		closeClick	: false,
		openEffect	: 'none',
		closeEffect	: 'none',
		type		: 'ajax',
		helpers:  {
			overlay: {
				locked: false
			}
		}
	});

	$(".pano-view").fancybox({
		padding     : 0,
		margin      : 10,
		fitToView	: false,
		closeClick	: false,
		openEffect	: 'none',
		closeEffect	: 'none',
		type		: 'ajax',
		wrapCSS : 'viewing',
		helpers:  {
			overlay: {
				locked: false
			}
		}
	});
	
	

	// datepicker
	$(".datepicker").datepicker({
		dateFormat: 'yy-mm-dd' //Input Display Format 변경
		,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
		,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시         
		//,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시
		,prevText: "이전달"
		,nextText: "다음달"
		,buttonText: "날짜선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트                
		,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
		,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
		,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
		,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
		,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
	}); 

	$(".datepicker2").datepicker({
		dateFormat: 'yy-mm-dd' //Input Display Format 변경
		,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
		,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시    
		,changeMonth: true //월 선택 표시
		,changeYear: true //년도 선택 표시
		,minDate: '-100y' // 현재날짜로부터 100년이전까지 년을 표시
		,yearRange: 'c-100:c+10' // 년도 선택 셀렉트박스를 현재 년도에서 이전, 이후로 얼마의 범위를 표시할것인가.
		//,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
		,prevText: "이전달"
		,nextText: "다음달"
		,buttonText: "날짜선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트                
		,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
		,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
		,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
		,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
	}); 

	// input
	$("input[type=tel], input[numberOnly]").on("keyup", function() {
		$(this).val($(this).val().replace(/[^0-9]/g,""));
	});

	
});
new WOW().init();



$(window).bind("load resize", function(){
	stageResize();
});

function stageResize(){
	winH = $(window).height(),
	docH = $(document).height(),
	headH = $("#header").outerHeight(),
	lnbH = $("#lnb").outerHeight(),
	svH = $(".sub-visual").outerHeight(),
	footH = $("#footer").outerHeight();

	$("#sub #container").css("min-height",winH-headH-svH-footH);

	
}
