<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.PO"
	import="util.CommonUtil"
	import="dao.PODao"
	import="java.util.ArrayList" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="header.jsp"></jsp:include>
<link rel="stylesheet" href="../../css/back/PO.css" />
<script src="../../js/PODetail.js"></script>
<%
	int pageNum = new CommonUtil().StringToInt(request.getParameter("pageNum"));
	if(pageNum < 0) pageNum = 0;
	String id = new CommonUtil().getString(request.getParameter("id"));
	System.out.println("id="+id);
	PODao dao = new PODao();
	PO po = new PO();
	po.setId(id);
	ArrayList<PO> poList = dao.query(po);
	request.setAttribute("poList", poList);
%>
<div class="content">
	<div>
		<div class="pageName">訂單明細查詢</div>
		<div class="search">
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
						<label>物流編號：${poList[0].freightId }</label>
					</td>
					<td>
						<label>貨運商名稱：${poList[0].freightName }</label>
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
	<div class="queryTitle">訂單明細</div>
	<div>
		<form id="queryForm" action="#" method="post">
			<input type="hidden" id="id" name="id" value="${poList[0].id }" > 
			<input id="pageNum" name="pageNum" type="hidden" value="<%=pageNum%>" >
			<table class="qTable">
				<tr>
					<th>商品編號</th>
					<th>商品名稱</th>
					<th>單價</th>
					<th>數量</th>
					<th>單位</th>
					<th>小記</th>
				</tr>
				<c:set var="pageRow" value="<%=pageNum * 10  %>" />
				<c:forEach items="${poList[0].poDetail }" var="detail" varStatus="vs">
					<c:if test="${vs.index >= pageRow && vs.index < pageRow + 10}" >
						<tr style='background-color:${vs.index % 2 == 0 ? "#FFF" : "#FDEBE0" }' >
							<td><a href="ProductDetail.jsp">${detail.wineId}</a></td>
							<td>${detail.wineName}</td>
							<td>${detail.price}</td>
							<td>${detail.quantity}</td>
							<td>${detail.unit}</td>
							<td>${detail.subtotal}</td>
						</tr>
					</c:if>
				</c:forEach>
				<c:if test="${empty poList[0].poDetail }">
					<tr><td colspan="7">查無資料</td></tr>
				</c:if>
			</table>
			<c:if test="${not empty poList[0].poDetail}">
				<div class="page">
					<div><input type="button" id="pre" name="pre" ${pageRow > 0 ? '' : 'disabled'} value="<"></div>
					<div class="showPageNum"><span id="page">&nbsp; <%=pageNum+1 %> &nbsp;</span></div>
					<div><input type="button" id="next" name="next" ${fn:length(poList[0].poDetail) > pageRow + 10 ? '' : 'disabled'} value=">"></div>
				</div>
			</c:if>
		</form>
	</div>
	<div class="footerButton">
		<input type="button" value="回訂單查詢" name="cancel" id="cancel" >
	</div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
