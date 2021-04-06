// top_menu
$(function(){
		$("#main_nav>ul>li").mouseenter(function(){
			var menuNum = $(this).index()+1;
			if(menuNum === menuNum){
				$(".sub_menu").stop().slideDown();
				$(".sub_menu > ul").stop().hide();
				$(".sub_menu > .sub"+menuNum+"").stop().show();
				//console.log(menuNum);
			}else{
				$(".sub_menu").stop().slideUp();
				$(".sub_menu > ul").stop().hide();
			}
		});
		$("#main_nav").mouseleave(function(){
			$(".sub_menu").stop().slideUp();
			$(".sub_menu > ul").stop().hide();
		});
		
		chkNum = 0;
		$(".toggle_menu").click(function(){
			if(chkNum == 0){
				$(this).find("span").addClass("on");
				$(".allmenu").slideDown();
				chkNum = 1;
			}else{
				$(this).find("span").removeClass("on");
				$(".allmenu").slideUp();
				chkNum = 0;
			}		
		});
		$(window).resize(function(){
			ww = $(window).width();
			if(ww < 1280){
				$("body").addClass("mobile");
				$(".allmenu .innerwrap .menubox ul").hide();
			}else{
				$("body").removeClass("mobile");
				$(".allmenu .innerwrap .menubox ul").show();
			}		
		}).resize();
		$(document).on("click",".mobile .allmenu .innerwrap .menubox h2",function(){
			if($(this).attr("class") == "slideM act"){				
				$(this).next("ul").slideUp(300);				
			}else{
				$(this).next("ul").slideDown(300);				
			}			
			$(this).toggleClass("act");
			$(this).parent(".menubox").siblings().find("h2").removeClass("act");
			$(this).parent(".menubox").siblings().find("ul").slideUp();
		})

		
		var lastScrollTop = 0, delta = 15;
		$(window).scroll(function(event){
			var st = $(this).scrollTop();

			if(Math.abs(lastScrollTop - st) <= delta)
			  return; 
			if ((st > lastScrollTop) && (lastScrollTop>0)) {
				$("#wrap header").css("top","-115px");
			 } else {
				 $("#wrap header").css("top","0px");
			}
			 lastScrollTop = st;
		});
	});

//main_visual

$(document).ready(function(){
	$(".main_txt .btxt").delay(400).animate({"opacity":"1"},1600);
	$(".main_txt .stxt").delay(800).animate({"opacity":"1"},1600);
});

$(document).ready(function(){
$('.section-1-slide').slick({
		slidesToShow: 4,
		autoplay: true,
		arrows:true,
	    lazyLoad: 'progressive',
		autoplaySpeed: 3000,
		dots:false,
		swipe:true,
		infinite: true,
		focusOnSelect: false,
		pauseOnHover:false,
		  responsive: [
			{
			  breakpoint: 1024,
			  settings: {
				slidesToShow: 1,
				slidesToScroll: 1,
			  }
			},
			{
			  breakpoint: 767,
			  settings: {
				slidesToShow: 1,
				slidesToScroll: 1
			  }
			}
		  ]
	 });
});

$(document).ready(function(){
	var footer_slider = $('#ft-cont').bxSlider({
	    auto: true,
	    autoStart: true,
	    pause : 3000,
	    minSlides: 1,
	    maxSlides: 6,
		moveSlides: 1,
	    slideWidth: 200,
	    slideMargin: 0,
	    infiniteLoop: true,
	    ticker: false, //true 
	    speed: 1000,
		touchEnabled : false,
		pager: false,
		controls: true,
		autoControls: true
	});
	// 슬라이드 auto스크롤 on (바로 실행시 auto동작 안함)
	setTimeout(function(){
		footer_slider.startAuto();
    }, 200);
});


$(document).ready(function(){
$('.top_nav a').on('click', function(event) {
	$(this).parent().find('a').removeClass('active');
	$(this).addClass('active');
	});
 });

$(window).on('scroll', function() {
    $('.target').each(function() {
        if($(window).scrollTop() >= $(this).offset().top) {
            var id = $(this).attr('id');
			 $('#section_nav nav a').removeClass('active');
			 $('#section_nav nav a[href=#'+ id +']').addClass('active'); 
        }
    });
});

//패밀리사이트//
$(document).ready(function(){
	$(".family-site h3").on("click", function() {
		$(".family-site ul").slideToggle();
		$(".family-site h3").toggleClass("active");
	}); 
});

//스크롤탑//
$(function(){$(window).scroll(function(){
	if ($(this).scrollTop() > 580) {$('#scroll_top').fadeIn()} 
	else { $('#scroll_top').fadeOut()};});
});
$('#scroll_top').on("click", function() {
	$('html, body').animate({scrollTop:0}, '500');
	return false;
});

//탭//
$(function(){
	$(".panel li:not("+$(".tab li a.selected").attr("href")+")").hide()
	
	$(".tab li a").click(function(){
	    $(".tab li a").removeClass("selected");
	    $(this).addClass("selected");
	    $(".panel li").hide();
	    $($(this).attr("href")).show();
	        return false;
	});
});
    
$(".tab > li > a.selected").attr("href") 
$(".panel > li:not("+$(".tab > li > a.selected").attr("href")+")")
$(".panel > li:not(#tab1)") 
    

//월드파트너 지도// 
$(document).ready(function() { 
	$(".partner-tab li.m-menu").click(function(){
		$(".partner-tab li ul").slideUp().parents('li').removeClass("h-menu").addClass("on-on");
		if(!$(this).find('ul').is(":visible")){
			$(this).find('ul').slideDown().parents('li').removeClass("on-on").addClass("h-menu");
		}
	});
	$("li.mark-1").on('click mouseover', function () {
		$(this).addClass('active');
		$("li.mark-1").siblings('li').removeClass('active');
		$(this).parents('div.map-img').addClass('img-1');
		$(this).parents('div.map-img').removeClass('img-2 img-3 img-4 img-5 img-6 img-7');				
		$(".m-title").addClass('tt-1').removeClass('tt-2 tt-3 tt-4 tt-5 tt-6 tt-7');
	});		

	$("li.mark-2").on('click mouseover', function () {
		$(this).addClass('active');
		$("li.mark-2").siblings('li').removeClass('active');
	  $(this).parents('div.map-img').addClass('img-2');
		$(this).parents('div.map-img').removeClass('img-1 img-3 img-4 img-5 img-6 img-7');			
	  $(".m-title").addClass('tt-2').removeClass('tt-1 tt-3 tt-4 tt-5 tt-6 tt-7');
	});		
	
	$("li.mark-3").on('click mouseover', function () {
		$(this).addClass('active');
		$("li.mark-3").siblings('li').removeClass('active');
	  $(this).parents('div.map-img').addClass('img-3');
		$(this).parents('div.map-img').removeClass('img-1 img-2 img-4 img-5 img-6 img-7');			
	  $(".m-title").addClass('tt-3').removeClass('tt-1 tt-2 tt-4 tt-5 tt-6 tt-7');
	});		
	
	$("li.mark-4").on('click mouseover', function () {
		$(this).addClass('active');
		$("li.mark-4").siblings('li').removeClass('active');
	  $(this).parents('div.map-img').addClass('img-4');
		$(this).parents('div.map-img').removeClass('img-1 img-2 img-3 img-5 img-6 img-7');			
	  $(".m-title").addClass('tt-4').removeClass('tt-1 tt-2 tt-3 tt-5 tt-6 tt-7');
	});		
	
	$("li.mark-5").on('click mouseover', function () {
		$(this).addClass('active');
		$("li.mark-5").siblings('li').removeClass('active');
	  $(this).parents('div.map-img').addClass('img-5');
		$(this).parents('div.map-img').removeClass('img-1 img-2 img-3 img-4 img-6 img-7');			
	  $(".m-title").addClass('tt-5').removeClass('tt-1 tt-2 tt-3 tt-4 tt-6 tt-7');
	});		
	
	$("li.mark-6").on('click mouseover', function () {
		$(this).addClass('active');
		$("li.mark-6").siblings('li').removeClass('active');
	  $(this).parents('div.map-img').addClass('img-6');
		$(this).parents('div.map-img').removeClass('img-1 img-2 img-3 img-4 img-5 img-7');			
	  $(".m-title").addClass('tt-6').removeClass('tt-1 tt-2 tt-3 tt-4 tt-5 tt-7');
	});		
	
	$("li.mark-7").on('click mouseover', function () {
		$(this).addClass('active');
		$("li.mark-7").siblings('li').removeClass('active');
	  $(this).parents('div.map-img').addClass('img-7');
		$(this).parents('div.map-img').removeClass('img-1 img-2 img-3 img-4 img-5 img-6');			
	  $(".m-title").addClass('tt-7').removeClass('tt-1 tt-2 tt-3 tt-4 tt-5 tt-6');
	});		

	$(".country-cont").hide();
    $(".country-cont:first").show();

    $("ul.tabs-2 li").click(function () {
        $("ul.tabs-2 li").removeClass("active").css("color", "#888");
        //$(this).addClass("active").css({"color": "darkred","font-weight": "bolder"});
        $(this).addClass("active").css("color", "#000");
        $(".country-cont").hide()
        var activeTab = $(this).attr("rel");
        $("#" + activeTab).fadeIn()		
    });	
		
    $("ul.over-box li").click(function () {
        $("ul.over-box li").removeClass("active").css("color", "#888");
        //$(this).addClass("active").css({"color": "darkred","font-weight": "bolder"});
        $(this).addClass("active").css("color", "#000");
        $(".country-cont").hide()
        var activeTab = $(this).attr("rel");
        $("#" + activeTab).fadeIn()		
    });	
	
	
    $(".tab_content_2").hide();
    $(".tab_content_2:first").show();

    $("ul.tabs-3 li").click(function () {
        $("ul.tabs-3 li").removeClass("active");
        //$(this).addClass("active").css({"color": "darkred","font-weight": "bolder"});
        $(this).addClass("active").css("color", "fff");
        $(".tab_content_2").hide()
        var activeTab = $(this).attr("rel");
        $("#" + activeTab).fadeIn()		
	});	
	
	$(".tab_content").hide();
    $(".tab_content:first").show();

    $("ul.tabs li").click(function () {
        $("ul.tabs li").removeClass("active").css("color", "#888");
        //$(this).addClass("active").css({"color": "darkred","font-weight": "bolder"});
        $(this).addClass("active").css("color", "#000");
        $(".tab_content").hide()
        var activeTab = $(this).attr("rel");
        $("#" + activeTab).fadeIn()		
    });	

	$(".location-inner > ul > li.location1 a, .location-inner > ul > li.location2 a").click(function  () {
		$(this).siblings().slideToggle();
	});
	$(".location-inner > ul > li").on("mouseleave",function  () {
		$(this).find(".location-2dep").hide();
		$(this).find(".location-3dep").hide();
	});
  
});

jQuery.fn.serializeObject = function() { var obj = null; try { if(this[0].tagName && this[0].tagName.toUpperCase() == "FORM" ) { var arr = this.serializeArray(); if(arr){ obj = {}; jQuery.each(arr, function() { obj[this.name] = this.value; }); } } }catch(e) { alert(e.message); }finally {} return obj; }


function send( url, data, callback, reqType ) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		type: 'POST',
		url: url,
		contentType: "application/json",
		dataType: "json",
		data: JSON.stringify( data ),
		beforeSend: function(xhr) {
			xhr.setRequestHeader( header, token );
		},
		async: true,
		cache: false,
		success: function( d ) {
			callback( d )
		},
		error: function( xhr, status, error ) {
			console.log( xhr );
			console.log( status );
			console.log( error );
			alert( '예기치 못한 에러가 발생하였습니다. 관리자에게 문의해주세요.' );
		}
	});
}