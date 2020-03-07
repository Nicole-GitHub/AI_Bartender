$(document).ready(function() {

	resize();
	$(window).resize(function () {
		resize();
	});
    function resize(){
		var bodyw = $(".body").height();
		windowResize(bodyw);
	}

//	//日期套件
//    $(".datepicker").datepicker({
//        changeMonth: true,
//        changeYear: true,
//        dateFormat:"yy-mm-dd"
//      });

	$("#add").click(function(){
		location.href="POAdd.jsp";
	});
	
	$("input[name=update]").click(function() {
		location.href="POUpdate.jsp";
	});
	

	$("#pre").click(function() {
		var pageNum = $("#pageNum").val();
		$("#queryForm").attr("action", "PO.jsp");
		if (pageNum > 0) {
			$("#pageNum").val(--pageNum);
		}
		$("#queryForm").submit();
	});

	$("#next").click(function() {
		var pageNum = $("#pageNum").val();
		$("#queryForm").attr("action", "PO.jsp");
		$("#pageNum").val(++pageNum);
		$("#queryForm").submit();
	});
});