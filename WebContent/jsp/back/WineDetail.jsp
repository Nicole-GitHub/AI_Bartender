<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.*"
	import="util.CommonUtil,util.DateUtil"
	import="dao.*"
	import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp"/>
<link rel="stylesheet" href="../../css/back/PO.css" />
<link rel="stylesheet" href="../../css/back/WineAdd_b3.css" />
<script src="../../js/back/WineAdd.js"></script>
<%
	WineDao dao = new WineDao();
	WineDao wineDao = new WineDao();
	CommonUtil comm = new CommonUtil();
	String id = comm.getString(request.getParameter("id"));
	
	ArrayList<Wine> wineList = new ArrayList<Wine>();
	if(!id.equals("")){
		Wine wine = new Wine();
		wine.setId(id);
		wineList = dao.query(wine);
	}
	//System.out.println(pageNum);
%>
<c:set var="wineList" value="<%=wineList %>" />

<div class="content">
	<div>
		<div class="pageName">商品明細查詢</div>
		<div class="addContent">
			<form id="form" action="../../WineServlet" enctype="multipart/form-data" method="post">
				<table class="contentTable">
					<tr>
						<td rowspan=10><img src="../../${wineList[0].imgPath }" width="300px"></td>
					</tr>
					<tr>
						<th><label>商品編號：</label></th>
						<td colspan="3"><span>${wineList[0].id}</span></td>
					</tr>
					<tr>
						<th><label>英文名稱：</label></th>
						<td colspan="3">${wineList[0].enName}</td>
					</tr>
					<tr>
						<th><label>中文名稱：</label></th>
						<td colspan="3">${wineList[0].chName}</td>
					</tr>
					<tr>
						<th><label>品種：</label></th>
						<td colspan="3">${wineList[0].grape}</td>
					</tr>
					<tr>
						<th><label>酒種：</label></th>
						<td>${wineList[0].type}</td>
						<th><label>產地：</label></th>
						<td>${wineList[0].place}</td>
					</tr>
					<tr>
						<th><label>酒精濃度：</label></th>
						<td>${wineList[0].percent}</td>
						<th><label>容量：</label></th>
						<td>${wineList[0].ml}</td>
					</tr>
					<tr>
						<th><label>銷售單位：</label></th>
						<td>${wineList[0].unit}</td>
						<th><label>價格：</label></th>
						<td>${wineList[0].price}</td>
					</tr>
					<tr>
						<th><label>商品狀態：</label></th>
						<td colspan="3">${wineList[0].status}</td>
					</tr>
					<tr>
						<th><label>特色：</label></th>
						<td colspan="3">${wineList[0].feature}</td>
					</tr>
				</table>
				<div class="footerButton">
					<input type="button" value="回商品查詢" name="cancel" id="cancel" >
				</div>
			</form>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
