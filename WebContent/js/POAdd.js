$(document).ready(function() {
    resize();

	$(window).resize(function () {
		resize();
    });
    
    //新增明細項目
    //.clone(true)才能讓clone出來的子元素也能有event功能
    $("#addDetail").click(function(){
        $('.qTable').append($("#tempRow").clone(true).removeAttr("id").css("display","table-row"));
        resize();
    });

    //選好商品後系統自動帶入價格並算出小計
    $("select[name=wineId]").change(function(){
        var price = $(this).find("option:selected").attr("price");
        var tr = $(this).parents("tr");
        var priceInp = tr.find("input[name=price]");
        var priceTd = priceInp.parent();
        var quantity = tr.find("input[name=quantity]").val();
        priceInp.val(price);
        priceTd.find("span").text(price);
        setSubTotal(tr,price,quantity);
    });

    //數量填好後自動算出小計
    $("input[name=quantity]").change(function(){
        var tr = $(this).parents("tr");
        var quantity = $(this).val();
        var price = tr.find("input[name=price]").val();
        setSubTotal(tr,price,quantity);
    });
    
    //刪除明細項目 
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
    
    //確定新增
	$("#add").click(function() {
		if(validate($("#owner").val(),"訂購人") && 
				validate($("#createUser").val(),"建立人")){
			add();
		}
    });
	
	//取消
	$("#cancel").click(function(){
		goSearchPage();
	})

    //自動記算小計
    function setSubTotal(tr,price,quantity){
        var subtotalInp = tr.find("input[name=subtotal]");
        var subtotalTd = subtotalInp.parent();
        var subtotal = price * quantity;
        subtotalInp.val(subtotal);
        subtotalTd.find("span").text(subtotal);
        setTotal();
    }
	
	//自動計算總金額
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

    //判斷陣列內的值是否有重複
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
        po.status="訂購成功";
        po.createUser=$("#createUser").val();
        po.updateUser=$("#createUser").val();
        po.poDetail = [];

        var orginValue = [];
        var duplicate = false;
        $("select[name=wineId]").each(function(){
        	//跳過第一個tempRow
        	if($(this).parents("tr").attr("id") == "tempRow"){
        		return true;
        	}
        	var value = $(this).val();
        	if(orginValue.Contains(value)){
        		alert("商品名稱重複或空值");
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
    
    //回到查詢頁
    function goSearchPage(){
		location.href="PO.jsp";
    }
});