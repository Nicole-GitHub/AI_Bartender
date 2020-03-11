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
			$(".bodyRight").add(".bgImg").height(bodyh);
		}
	}

	// 數量減
    $("input[name=minus]").click(function(){
    	var tr = $(this).parents("tr");
    	var quantity = parseInt(tr.find("input[name=quantity]").val()) - 1;
    	var price = tr.find("input[name=price]").val();
    
//    	console.log(quantity);
    	if(quantity < 1){
    		alert("購買數量不可低於1");
    	}else{
    		setSubTotal(tr,price,quantity);
    	}
    });
    // 數量加
    $("input[name=plus]").click(function(){
    	var tr = $(this).parents("tr");
    	var quantity = parseInt(tr.find("input[name=quantity]").val()) + 1;
    	var price = tr.find("input[name=price]").val();
    
//    	console.log(quantity);
    	if(quantity > 99){
    		alert("購買數量不可超過99");
    	}else{
    		setSubTotal(tr,price,quantity);
    	}
    });
	
	// 自動記算小計並將金額顯示成一般貨幣的顯示方式
    function setSubTotal(tr,price,quantity){

		tr.find(".quantity").text(quantity);
		tr.find("input[name=quantity]").val(quantity);
		var subtotal = quantity * price;
		tr.find("input[name=subtotal]").val(subtotal);
		tr.find(".subtotal").text('$'+chg2Currency(subtotal));
		
        setTotal();
    }
	
	// 自動計算總金額
	function setTotal(){
        var total = 0;
        $("input[name=subtotal]").each(function(){
        	var thisVal = $(this).val();
        	if(thisVal != ""){
            	total += parseInt(thisVal);
        	}
        });
    	$("#total").val(total);
    	$("#totalTD").find("span").text(chg2Currency(total));
	}
	
	//將金額顯示成一般貨幣的顯示方式
	function chg2Currency(subtotal){
		var subtotalStr = subtotal.toString();
		var subtotalStrLen = subtotalStr.length;
		var subtotalStrQuot = subtotalStrLen / 3;
		var subtotalStrMod = subtotalStrLen % 3;
		var subtotalRSArr = [];
		var subtotalRS ;
		var getLen = 3;
		subtotalRS = subtotalStr;
		
		// 若金額超過三位數
		if(subtotalStrQuot > 1){
			subtotalRS = ''; //先清空存放結果值的欄位
			subtotalStrQuot = parseInt(subtotalStrQuot); //取商的整數
			for(var i = 0 ; i < subtotalStrQuot + 1 ; i++){
				subtotalStrLen = subtotalStr.length; //重新取長度
				if(subtotalStrQuot == i){ //若已取到最後一截，則重設截取長度為剩餘長度
					getLen = subtotalStrMod;
				}
				//先從最後面的三位數開始取
				subtotalRSArr[i] = subtotalStr.substring(subtotalStrLen - getLen);
				//再把已取出的三位數從原金額內去除
				subtotalStr = subtotalStr.substring(0,subtotalStrLen - getLen);
				//把剛剛取到的三位數加上貨幣的分隔符號後放進結果值內
				subtotalRS = ','+subtotalRSArr[i]+subtotalRS;
//				console.log(subtotalRS);
			}
			//若原金額的長度剛好被3整除則結果會變成前面有兩個","所以要從第3碼開始取
			subtotalRS = subtotalRS.substring(subtotalStrMod > 0 ? 1 : 2,subtotalRS.length);
		}
		
		return subtotalRS;
	}
	
	// 新增
	$("#send").click(function() {
        var common = new Object();
        common.action = $("#action").val();
        var po=new Object();
        po.id=$("#poId").val();
        po.total=$("#total").val();
        po.owner=$("#owner").val(); 
        po.status=$("#status").val();
        po.createUser=$("#owner").val();
        po.updateUser=$("#owner").val();
        po.poDetail = [];

        var orginValue = [];
        var duplicate = false;
        $(".sendVal").each(function(){
            var poDetail=new Object();
            poDetail.poId = po.id;
            poDetail.wineId = $(this).find("input[name=wineId]").val();
            poDetail.price = $(this).find("input[name=price]").val();
            poDetail.quantity = $(this).find("input[name=quantity]").val();
            poDetail.unit = $(this).find("input[name=unit]").val();
            poDetail.subtotal = $(this).find("input[name=subtotal]").val();
            po.poDetail.push(poDetail);
        });
        if(!duplicate){
	        $.post("../../POServlet",{"po":JSON.stringify(po),"common":JSON.stringify(common)},function(rs){
	        	if(rs == "ok"){
	        		location.href="PO.jsp";
	        	}
	        });
        }
    });
	
	
	
	//取消訂購
	$("img[name=del]").click(function() {
        var common = new Object();
		common.action = "del";
		console.log("del");

        $.post("../../POServlet",
        		{
        	"id":$(this).attr("uuid"),
        	"owner":$(this).attr("owner"),
        	"common":JSON.stringify(common)
        	},function(rs){
			console.log(rs);
			if(rs == "ok"){
        		location.reload();
        	}
        });
	});

	//刪除訂單明細
	$("img[name=delTR]").click(function() {
    	var c = 0;
    	$(this).parents("table").find("tr").each(function(){
    		c++;
    	});
    	if(c == 5){
    		$("#send").add("#totalTD").css("display","none");
    		$("#emptyCart").css("display","table-row");
    		$("table th").addClass("addBorderBottom");
        }
		$(this).parents("tr").remove();
        resize();
		setTotal();
	});
});