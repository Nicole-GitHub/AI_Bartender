$(document).ready(function() {
	
	resize();
	$(window).resize(function () {
		resize();
	});
	
    function resize(){
		var bodyh = $(".content").height();
		windowResize(bodyh);
	}

	function windowResize(bodyh){
		bodyh += 50;
		var vh = $(window).height();
		var headerh = $(".header").height();
		var banner = $(".banner").height();
		var banner2 = $(".banner2").height();
		var footerh = $(".footer").height();
		var otherh = vh - headerh - footerh - banner - banner2;
		if (bodyh < otherh) {
			$(".footer").addClass("positionFix");
			$(".bodyRight").add(".bgImg").height(otherh);
		}else{
			$(".footer").removeClass("positionFix");
			$(".bodyRight").height(bodyh);
		}
	}
});