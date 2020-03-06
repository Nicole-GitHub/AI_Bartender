<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.PO"
	import="util.CommonUtil"
	import="dao.PODao"
	import="java.util.ArrayList" %>

<jsp:include page="../common/header.jsp"></jsp:include>
<style>
</style>
<%
	int pageNum = new CommonUtil().StringToInt(request.getParameter("pageNum"));
	if(pageNum < 0) pageNum = 0;
	//System.out.println(pageNum);
%>
<div class="body">
	<div>
		<div><h1><b>修改訂單明細</b></h1></div>
		<div>
			<table style="margin:auto;width:60%;min-width:500px;text-align:left">
				<tr>
					<td>
						<label>訂單編號：o001</label>
					</td>
					<td>
						<label>訂購人：王小明</label>
					</td>
				</tr>
				<tr>
					<td>
						<label>訂單狀態：待處理</label>
					</td>
					<td>
						<label>總金額：300</label>
					</td>
				</tr>
				<tr>
					<td>
						<label>建立人員：admin</label>
					</td>
					<td>
						<label>建立時間：2020/01/01 13:30:00</label>
					</td>
				</tr>
				<tr>
					<td>
						<label>最後修改人：admin</label>
					</td>
					<td>
						<label>最後修改時間：2020/01/01 13:30:00</label>
					</td>
				</tr>
			</table><!--queryForm !-->
		</div>
	</div><!--panel-->
	<div><b>訂單明細</b><input type="button" value="新增明細" name="addDetail" id="addDetail"></div>
	<div>
		<table class="qTable" style="padding:0;">
			<tr>
				<th>商品名稱</th>
				<th>單價</th>
				<th>數量</th>
				<th>單位</th>
				<th>小記</th>
				<th>刪除</th>
			</tr>
			<%
				System.out.println("query");
					ArrayList<PO> arr = new PODao().query(null);
					int j = 0;
					if (arr.size() > 0) {
						int pageRow = pageNum * 10;
						for (int i = pageRow ; i < arr.size() && i < (pageRow + 10) ; i++) {
			%>
			<tr style='background-color:<%=j%2==0 ? "#f6c184" : "#f9dbb8" %>' >
				<td>
					<select>
						<option value="">請選擇</option>
						<option value="P001">P001 Vina Quebrada de Macul Domus Aurea Cabernet Sauvignon, Maipo Valley, Chile </option>
						<option value="P002">P002 2018 Torley Campanula Pinot Grigio, Etyek-Buda, Hungary </option>
						<option value="P003">P003 2016 Bodegas Luis Canas Blanco Fermentado en Barrica, Rioja DOCa, Spain </option>
						<option value="P004">P004 2014 Sieur d'Arques Cremant de Limoux Toques et Clochers, Languedoc-Roussillon, France </option>
						<option value="P005">P005 2018 Domaine de Triennes Rose, IGP Var, France </option>
					</select>
				</td>
				<td><input type="number" ></td>
				<td><input type="number" ></td>
				<td><input type="text" ></td>
				<td><input type="number" ></td>
				<td><input type="button" value="刪除" name="delete" id="delete"></td>
			</tr>
			<%
						j++;
					}
			%>
				<tr>
					<td colspan="14">
					<br>
						<% if(pageRow > 0){ %>
						<input type="button" id="pre" name="pre" value="上一頁">
						<% }
						   if(arr.size() > pageRow + 10){ %>
						<input type="button" id="next" name="next" value="下一頁">
						<% } %>
						<span>&nbsp;&nbsp;&nbsp;&nbsp;第<%=pageNum+1 %>頁</span>
					</td>
				</tr>
			<%
				} else {
			%>
			<tr>
				<td colspan="14">查無資料，請先新增</td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	
	<div>
		<input type="button" value="確定新增" name="update" id="update">
		<input type="button" value="取消" name="return" id="return" >
	</div>
</div>

<script>
	$(document).ready(function() {
		$("input[name=del]").click(function() {
			$("#id").val($(this).attr("uuid"));
			$("#action").val("del");
			$("#form").attr("action", "action.jsp");
			if(<%=j == 1%>){
				$("#pageNum").val(0);
			}
			$("#form").submit();
		});
		$("#return").click(function(){
			location.href = "PO.jsp";
		})
	});
</script>
<jsp:include page="../common/footer.jsp"></jsp:include>
