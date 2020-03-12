$(document).ready(function() {

	resize();
	$(window).resize(function () {
		resize();
	});
	
    function resize(){
		var bodyh = $(".content").height();
		windowResize(bodyh);
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
    	$("#searchForm select , #searchForm input ").each(function(){
    		$(this).val("");
    	});

    	$("#searchForm").submit();
	});
    //新增
	$("#add").click(function(){
		location.href="WineAdd.jsp";
	});
	
	//修改
	$("input[name=update]").click(function() {
		location.href="WineAdd.jsp?id="+$(this).attr("uuid");
	});
	
	//刪除
	$("input[name=del]").click(function() {
        var common = new Object();
		common.action = "del";

        $.post("../../WineServlet",
        		{
        	"id":$(this).attr("uuid"),
        	"common":JSON.stringify(common)
        	},function(rs){
			console.log(rs);
			if(rs == "ok"){
        		location.reload();
        	}
        });
	});
	
	//上一頁
	$("#pre").click(function() {
		var pageNum = $("#pageNum").val();
		$("#queryForm").attr("action", "Wine.jsp");
		if (pageNum > 0) {
			$("#pageNum").val(--pageNum);
		}
		$("#queryForm").submit();
	});
	
	//下一頁
	$("#next").click(function() {
		var pageNum = $("#pageNum").val();
		$("#queryForm").attr("action", "Wine.jsp");
		$("#pageNum").val(++pageNum);
		$("#queryForm").submit();
	});
	
	//從Excel匯入
	$("#uploadXlsx").change(function(){
		$("#uploadForm").submit();
	});
	
	//匯出至Excel
	$("#export").click(function(){
		location.href="../../Export";
	});
	
	//下載匯入商品資訊範例檔
	$("#getSample").click(function(){
		location.href="../../Download";
	});
});