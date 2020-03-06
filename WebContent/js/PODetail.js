$(document).ready(function() {

	resize();
	$(window).resize(function () {
		resize();
	});
    function resize(){
		var bodyw = $(".body").height();
		windowResize(bodyw);
	}	
	
	$("#pre").click(function() {
		var pageNum = $("#pageNum").val();
		$("#form").attr("action", "query.jsp");
		if (pageNum > 0) {
			$("#pageNum").val(--pageNum);
		}
		$("#form").submit();
	});

	$("#next").click(function() {
		var pageNum = $("#pageNum").val();
		$("#form").attr("action", "query.jsp");
		$("#pageNum").val(++pageNum);
		$("#form").submit();
	});

});