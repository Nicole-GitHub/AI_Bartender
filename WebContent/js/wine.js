
$(document).ready(function(){
	resize();

	$(window).resize(function () {
		resize();
	});
	
    function resize(){
		var filterh = $(".filter").height();
		var wineh = $(".showWine").height();
		windowResize(wineh+filterh);
	}
	
	var slideSpeed=50;
	var selects=$(".select");
	selects.hover(function(){
		$(this).find(".option").slideDown(slideSpeed);
		$(this).find("p").css("background","#F9E7AA");
	},function(){
		$(this).find(".option").slideUp(slideSpeed);
		$(this).find("p").css("background","#fff");
	});

})