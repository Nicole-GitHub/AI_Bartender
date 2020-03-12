$(document).ready(function() {

	resize();
	$(window).resize(function () {
		resize();
	});
	
    function resize(){
		var bodyh = $(".content").height();
		windowResize(bodyh);
	}
	
    //取消
	$("#cancel").click(function(){
		location.href="Wine.jsp";
	})

});