
$(document).ready(function(){
	resize();

	$(window).resize(function () {
		resize();
	});
	
    function resize(){
		var filterw = $(".filter").height();
		var winew = $(".showWine").height();
		windowResize(winew+filterw);
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