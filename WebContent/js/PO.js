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

    //查詢
    $("#search").click(function(){
    	$("#searchForm").submit();
	});

    //清除
    $("#reset").click(function(){
    	$("#searchForm select").each(function(){
    		$(this).val("");
    	});
	});
    //新增
	$("#add").click(function(){
		location.href="POAdd.jsp";
	});
	
	//修改
	$("input[name=update]").click(function() {
		location.href="POUpdate.jsp";
	});
	
	//上一頁
	$("#pre").click(function() {
		var pageNum = $("#pageNum").val();
		$("#queryForm").attr("action", "PO.jsp");
		if (pageNum > 0) {
			$("#pageNum").val(--pageNum);
		}
		$("#queryForm").submit();
	});
	
	//下一頁
	$("#next").click(function() {
		var pageNum = $("#pageNum").val();
		$("#queryForm").attr("action", "PO.jsp");
		$("#pageNum").val(++pageNum);
		$("#queryForm").submit();
	});
});