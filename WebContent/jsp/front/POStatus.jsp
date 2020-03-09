<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="dao.POStatusDao,model.POStatus,util.CommonUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="header.jsp"/>
<link rel="stylesheet" href="../../css/front/PO.css"/>
<script src="../../js/front/PO.js"></script>
<%
	CommonUtil comm = new CommonUtil();
	String poId = comm.getString(request.getParameter("poId"));
	String freightId = comm.getString(request.getParameter("freightId"));
	String freightName = comm.getString(request.getParameter("freighName"));
%>
<div class="body">
	<div class="banner2"><img src="../../imgs/common/WebPhoto/photo-04.jpg" style="width:100%"></div>
	<div class="bgImg">
		<div class="content">
			<table class="title">
				<tr>
					<td style="width:15%">詢問單狀態</td>
					<td style="width:25%">詢問單號：<%=poId %></td>
					<td style="width:5%">|</td>
					<td style="width:25%">貨運商：<%=freightId %></td>
					<td style="width:5%">|</td>
					<td style="width:25%">物流編號：<%=freightName %></td>
				</tr>
			</table>
			
			<table>
				<tr>
					<th>詢問單狀態</th>
					<th>狀態變更時間</th>
				</tr>
				<c:forEach items='<%=new POStatusDao().query(poId,"") %>' var="poStatus">
					<tr>
						<td>${poStatus.poStatus }</td>
						<td>${poStatus.updateTime }</td>
					</tr>
				</c:forEach>
			</table>
			<input type="button" id="cancel" name="cancel" value="回詢問單" onclick="location.href='PO.jsp'">
		</div>
	</div>
</div>
<jsp:include page="footer.jsp"/>