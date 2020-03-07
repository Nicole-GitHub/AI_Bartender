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

    	$("#searchForm").submit();
	});
    //新增
	$("#add").click(function(){
		location.href="POAdd.jsp";
	});
	
	//修改
	$("input[name=update]").click(function() {
		location.href="POAdd.jsp?id="+$(this).attr("uuid");
	});
	
	//取消訂購
	$("input[name=del]").click(function() {
        var common = new Object();
        common.action = "del";
        $.post("../../POServlet",{"id":$(this).attr("uuid"),"common":JSON.stringify(common)},function(rs){
        	if(rs == "ok"){
        		location.reload();
        	}
        });
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