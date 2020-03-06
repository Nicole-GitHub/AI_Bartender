<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.PO"
	import="util.CommonUtil"
	import="dao.PODao"
	import="java.util.ArrayList" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../common/header.jsp"></jsp:include>
<link rel="stylesheet" href="../../css/PO.css" />
<script src="../../js/PODetail.js"></script>
<style>
</style>
<%
	int pageNum = new CommonUtil().StringToInt(request.getParameter("pageNum"));
	if(pageNum < 0) pageNum = 0;
	String id = new CommonUtil().getString(request.getParameter("id"));
	System.out.println(id);
	PO po = new PO();
	po.setId(id);
	ArrayList<PO> poList = new PODao().query(po);
	request.setAttribute("poList", poList);
%>
<div class="body">
	<div>
		<div><h1><b>訂單明細查詢</b></h1></div>
		<div>
			<table class="titleTable">
				<tr>
					<td>
						<label>訂單編號：${poList[0].id }</label>
					</td>
					<td>
						<label>訂購人：${poList[0].owner }</label>
					</td>
				</tr>
				<tr>
					<td>
						<label>訂單狀態：${poList[0].status }</label>
					</td>
					<td>
						<label>總金額：${poList[0].total }</label>
					</td>
				</tr>
				<tr>
					<td>
						<label>建立人員：${poList[0].createUser }</label>
					</td>
					<td>
						<label>建立時間：${poList[0].createTime }</label>
					</td>
				</tr>
				<tr>
					<td>
						<label>最後修改人：${poList[0].updateUser }</label>
					</td>
					<td>
						<label>最後修改時間：${poList[0].updateTime }</label>
					</td>
				</tr>
			</table><!--queryForm !-->
		</div>
	</div><!--panel-->
	<div><h3><b>訂單明細</b></h3></div>
	<div>
		<table class="qTable">
			<tr>
				<th>商品編號</th>
				<th>單價</th>
				<th>數量</th>
				<th>單位</th>
				<th>小記</th>
			</tr>
			<c:forEach items="${poList[0].poDetail }" var="detail" varStatus="vs">
				<tr style='background-color:${vs.index % 2 == 0} ? "#f6c184" : "#f9dbb8" %>' >
					<td><a href="ProductDetail.jsp">${detail.wineId}</a></td>
					<td>${detail.price}</td>
					<td>${detail.quantity}</td>
					<td>${detail.unit}</td>
					<td>${detail.subtotal}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<div>
		<input type="button" value="修改" name="update" id="update">
		<input type="button" value="回訂單查詢頁" name="return" id="return" >
	</div>
</div>

<jsp:include page="../common/footer.jsp"></jsp:include>
