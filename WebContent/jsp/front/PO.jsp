<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="dao.PODao,model.PO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="header.jsp"/>

<link rel="stylesheet" href="../../css/front/PO.css"/>
<script src="../../js/front/PO.js"></script>
<div class="body">
	<div class="banner2"><img src="../../imgs/common/WebPhoto/photo-03.jpg" style="width:100%"></div>
	<div class="bgImg">
		<div class="content">
			<p>詢問單</p>
			<table>
				<tr>
					<th>詢問單號</th>
					<th>下單時間</th>
					<th>總金額</th>
					<th>詢問單狀態</th>
					<th></th>
				</tr>
				<% PODao dao = new PODao();
					PO po = new PO();
					po.setOwner("nicole@gmail.com");
				%>
				<c:forEach items="<%=new PODao().query(po) %>" var="po">
					<tr>
						<td><a href="PODetail.jsp?poId=${po.id }">${po.id }</a></td>
						<td>${po.createTime }</td>
						<td>${po.total }</td>
						<td><a href="POStatus.jsp?poId=${po.id }&freightId=${po.freightId }&freightName=${po.freightName }">${po.status }</a></td>
						<td>X</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>
<jsp:include page="footer.jsp"/>