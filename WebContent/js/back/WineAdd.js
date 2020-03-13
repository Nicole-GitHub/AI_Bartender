$(document).ready(function() {

	resize();
	$(window).resize(function () {
		resize();
	});
	
    function resize(){
		var bodyh = $(".content").height();
		windowResize(bodyh);
	}

    // 確定新增
	$("#add").click(function() {
//		if(validate($("#enName").val(),"訂購人") && validate($("#status").val(),"訂單狀態")){
			$("#form").submit();
//		}
    });
	
	// 取消
	$("#cancel").click(function(){
		location.href="Wine.jsp";
	})

    // 驗証欄位是否必填
    function validate(val,name){
        if(val.trim().length > 0){
            return true;
        }else{
            alert(name+"不可為空");
            return false;
        }
    }
});