
	$(document).ready(function() {
		
		/**
		 * 新增/修改
		 */
		$("#edit").click(function() {
			if(valid("name","請問尊姓大名") && valid("product1") && valid("product2") && valid("product3") && valid("product4") && valid("product5")
					 && valid("add1") && valid("add2") && valid("add3") && valid("add4") && valid("add5")){
				if(gl0("product1") || gl0("product2") || gl0("product3") || gl0("product4") || gl0("product5")){
					$("#form").submit();
				}else{
					alert($("#name").val()+" 您不點餐嗎?");
				}
			}
		});
		
		/**
		 * 加點配菜
		 */
		$("#add").click(function(){
			if($(this).prop("checked")){
				if(valid("product1") && valid("product2") && valid("product3") && valid("product4") && valid("product5")){
					if(gl0("product1") || gl0("product2") || gl0("product3") || gl0("product4") || gl0("product5")){
						$(".add").show();
					}else{
						$(this).removeAttr("checked");
						alert("請先點主餐再加點配菜");
					}
				}else{
					$(this).removeAttr("checked");
				}
			}else{
				$(".add").hide();
				$(".add").find("input").val(0);
			}
		});
	});
	
	/**
	 * 大於0
	 * @param s
	 * @returns
	 */
	function gl0(s){
		return $("#"+s).val() > 0;
	}
	
	/**
	 * 驗証 空值、最大數值
	 * 遇到小數點自動去小數(不alert)
	 * @param s
	 * @param n
	 * @returns
	 */
	function valid (s,n){
		var val = $("#"+s).val().trim();
		if(val == "" && s == "name"){
			alert(n);
			return false;
		}else if((val == "" || val < 0) && s != "name"){
			alert("餐點數量不可為負數或空值");
			return false;
		}else if(val > 9999 && s != "name"){
			alert("最大 公克/份 數為 9999 ");
			return false;
		}
		
		if(s != "name"){
			point = val.indexOf(".");
			if(point > 0){
				val = val.substring(0,val.indexOf("."));
				$("#"+s).val(val);
			}
		}
		return true;
	}