$(document).ready(function() {
    resize();

	$(window).resize(function () {
		resize();
    });
    
    // 新增明細項目
    // .clone(true)才能讓clone出來的子元素也能有event功能
    $("#addDetail").click(function(){
        $('.qTable').append($("#tempRow").clone(true).removeAttr("id").css("display","table-row"));
        resize();
    });

    // 存放status的舊值
    var oldStatus ;
    $("#status").focus(function(){
    	oldStatus = $(this).val();
    })
    // 判斷狀態變更是否正確
    $("#status").change(function(){
    	var oldDBStatus = $("#oldStatus").val();
    	var status = $(this).val();
    	var o = 0;
    	var n = 0;
    	var statusList = ["","等待專員聯繫","訂購成功","撿貨中","理貨中","已出貨","已完成","已取消"];
    	$.each(statusList, function(index,val) {
    		if(oldDBStatus == val) o = index;
    		if(status == val) n = index;
    		console.log('val='+val+",index="+index);
    	}); 

		if(n < o){
			alert("訂單狀態設定錯誤");
        	$(this).val(oldStatus);
			return false;
        }else{
        	oldStatus = status;
        }
    });
    
    // 存放wineId的舊值
    var oldWineId ;
    $("select[name=wineId]").focus(function(){
    	oldWineId = $(this).val();
    })
    // 選好商品後系統自動帶入價格並算出小計
    $("select[name=wineId]").change(function(){
        var wineId = $(this).val();
        if(wineId == ""){
        	alert("商品名稱不可為空");
        	$(this).val(oldWineId);
    		return false;
        }else{
        	oldWineId = wineId;
        }
        var price = $(this).find("option:selected").attr("price");
        var tr = $(this).parents("tr");
        var priceInp = tr.find("input[name=price]");
        var priceTd = priceInp.parent();
        var quantityInp = tr.find("input[name=quantity]");
        price = price == null ? 0 : price;
        priceInp.val(price);
        priceTd.find("span").text(price);
    	quantityInp.val(1);

        setSubTotal(tr,price,1);
    });

    // 存放quantity的舊值
    var oldquantity ;
    $("input[name=quantity]").focus(function(){
    	oldquantity = $(this).val();
    })
    // 數量填好後自動算出小計
    $("input[name=quantity]").change(function(){
        var tr = $(this).parents("tr");
        var quantity = $(this).val();
        if(quantity < 1){
        	alert("數量不可為0或為負");
        	$(this).val(oldquantity);
    		return false;
        }else{
        	oldquantity = quantity;
        }
        var price = tr.find("input[name=price]").val();
        setSubTotal(tr,price,quantity);
    });
    // 刪除明細項目
    $("input[name=delete]").click(function(){
    	var c = 0;
    	$(this).parents("table").find("tr").each(function(){
    		c++;
    	});
    	if(c > 3){
	        $(this).parents("tr").remove();
	        setTotal();
	        resize();
        }else{
        	alert("至少要有一筆訂單明細");
        }
    });
    
    // 確定新增
	$("#add").click(function() {
		if(validate($("#owner").val(),"訂購人") && validate($("#status").val(),"訂單狀態")){
			add();
		}
    });
	
	// 取消
	$("#cancel").click(function(){
		goSearchPage();
	})

    // 自動記算小計
    function setSubTotal(tr,price,quantity){
        var subtotalInp = tr.find("input[name=subtotal]");
        var subtotalTd = subtotalInp.parent();
        var subtotal = price * quantity;
        subtotalInp.val(subtotal);
        subtotalTd.find("span").text(subtotal);
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
    	$("#total").parent().find("span").text(total);
	}
    // 重新調整footer位置
    function resize(){
        var bodyw = $(".body").height();
		windowResize(bodyw);
    }

    // 判斷訂單明細內的商品名稱陣列內的值是否有重複
    Array.prototype.Contains = function(element) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == element) {
                return true;
            }
        }
        return false;
    };
    
    // 新增
    function add(){
        var common = new Object();
        common.action = $("#action").val();
        var po=new Object();
        po.id=$("#id").val();
        po.total=$("#total").val();
        po.owner=$("#owner").val(); 
        po.status=$("#status").val();
        po.freightId=$("#freightId").val();
        po.freightName=$("#freightName").val();
        var user = $("#action").val() == "add" ? $("#createUser") : $("#updateUser");
        po.createUser=user.val();
        po.updateUser=user.val();
        po.poDetail = [];

        var orginValue = [];
        var duplicate = false;
        $("select[name=wineId]").each(function(){
        	// 跳過第一個tempRow
        	if($(this).parents("tr").attr("id") == "tempRow"){
        		return true;
        	}
        	var value = $(this).val();
        	if(orginValue.Contains(value)){
        		alert("商品名稱重複");
        		duplicate = true;
        		return false;
        	}
        	orginValue.push(value);
            var poDetail=new Object();
            poDetail.poId = po.id;
            poDetail.wineId = $(this).val();
            poDetail.price = $(this).parents("tr").find("input[name=price]").val();
            poDetail.quantity = $(this).parents("tr").find("input[name=quantity]").val();
            poDetail.unit = $(this).parents("tr").find("input[name=unit]").val();
            poDetail.subtotal = $(this).parents("tr").find("input[name=subtotal]").val();
            if(poDetail.wineId != ""){
                po.poDetail.push(poDetail);
            }
        });
        if(po.poDetail == ""){
        	alert("請選擇商品名稱");
        	return false;
        }
        if(!duplicate){
	        $.post("../../POServlet",{"po":JSON.stringify(po),"common":JSON.stringify(common)},function(rs){
	        	if(rs == "ok"){
	        		goSearchPage();
	        	}
	        });
        }
    }

    // 驗証欄位是否必填
    function validate(val,name){
        if(val.trim().length > 0){
            return true;
        }else{
            alert(name+"不可為空");
            return false;
        }
    }
    
    // 回到查詢頁
    function goSearchPage(){
		location.href="PO.jsp";
    }
});