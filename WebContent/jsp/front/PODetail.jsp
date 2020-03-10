<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="dao.PODao,model.PO,util.CommonUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp"/>
<link rel="stylesheet" href="../../css/front/PO.css"/>
<script src="../../js/front/PO.js"></script>
<%
	CommonUtil comm = new CommonUtil();
	String poId = comm.getString(request.getParameter("poId"));
	String total = comm.getString(request.getParameter("total"));
	request.setAttribute("total", total);
%>
<div class="body">
	<div class="banner2"><img src="../../imgs/common/WebPhoto/photo-04.jpg" style="width:100%"></div>
	<div class="bgImg">
		<div class="content">
			<p><span>詢問單明細</span>&emsp;<span><%=poId %></span></p>
			
			<table>
				<tr>
					<th>產品</th>
					<th>資訊</th>
					<th>數量</th>
					<th>金額</th>
					<th>小計</th>
				</tr>
				<c:forEach items='<%=new PODao().queryPODetailV(poId) %>' var="PODetailV">
					<tr>
						<td width="10%"><img src="../../${PODetailV.imgPath }" style="height:8vh"></td>
						<td width="45%" style="text-align: left;">
							<div id="chName">${PODetailV.wineChName }</div>
							<div>產地：${PODetailV.place }</div>
							<div>品種：${PODetailV.grape }</div>
						</td>
						<td width="10%">${PODetailV.quantity }</td>
						<td width="15%"><fmt:formatNumber value="${PODetailV.price}" type="number" pattern="$###,###" /></td>
						<td width="20%"><fmt:formatNumber value="${PODetailV.subtotal}" type="number" pattern="$###,###" /></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="5" style="border-top:0;text-align:right">
						總金額&emsp;<fmt:formatNumber value="${total}" type="number" pattern="$###,###" />
					</td>
				</tr>
			</table>
			<input type="button" id="cancel" name="cancel" value="回詢問單" onclick="location.href='PO.jsp'">
		</div>
	</div>
</div>
<jsp:include page="footer.jsp"/>