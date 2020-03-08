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
		location.href="PO.jsp";
	})
	
	//上一頁
	$("#pre").click(function() {
		var pageNum = $("#pageNum").val();
		$("#queryForm").attr("action", "PODetail.jsp");
		if (pageNum > 0) {
			$("#pageNum").val(--pageNum);
		}
		$("#queryForm").submit();
	});

	//下一頁
	$("#next").click(function() {
		var pageNum = $("#pageNum").val();
		$("#queryForm").attr("action", "PODetail.jsp");
		$("#pageNum").val(++pageNum);
		$("#queryForm").submit();
	});

});