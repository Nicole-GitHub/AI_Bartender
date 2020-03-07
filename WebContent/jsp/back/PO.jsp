<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="model.PO,util.CommonUtil,dao.PODao,java.util.ArrayList" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="../common/header.jsp"></jsp:include>
<link rel="stylesheet" href="../../css/PO.css" />
<script src="../../js/PO.js"></script>
<% 
	request.setCharacterEncoding("UTF-8");

	CommonUtil comm = new CommonUtil();
	PO po = new PO();
	PODao dao = new PODao();
	int pageNum = comm.StringToInt(request.getParameter("pageNum"));
	if(pageNum < 0) pageNum = 0;
	String owner = comm.getString(request.getParameter("owner"));
	String status = comm.getString(request.getParameter("status"));
	
	po.setOwner(owner);
	po.setStatus(status);
	System.out.println("owner="+owner);
	System.out.println("status="+status);
%>
<c:set var="owner" value="<%=owner%>" />
<c:set var="pageRow" value="<%=pageNum * 10  %>" />
<c:set var="poList" value="<%=dao.query(po)  %>" />
<div class="body">
	<div>
		<div><h1><b>訂單處理與查詢</b></h1></div>
		<div>
			<form id="searchForm" action="PO.jsp" method="post">
				<table class="titleTable">
					<tr>
						<td>
							<label>訂購人：</label>
							<select id="owner" name="owner">
								<option value="">=== 請選擇 ===</option>
								<c:forEach items="<%=dao.getUsersList() %>" var="user">
									<option value="${user.email }" ${user.email.equals(owner) ? "selected" : "" }> ${user.name } (${user.email }) </option>
								</c:forEach>
							</select>
							
						</td>
						<td>
							<label>訂單狀態：</label>
							<select id="status" name="status">
								<option value="">=== 請選擇 ===</option>
								<option value="等待專員聯繫" <%=status.equals("等待專員聯繫") ? "selected" : "" %>>等待專員聯繫</option>
								<option value="訂購成功" <%=status.equals("訂購成功") ? "selected" : "" %>>訂購成功</option>
								<option value="撿貨中" <%=status.equals("撿貨中") ? "selected" : "" %>>撿貨中</option>
								<option value="理貨中" <%=status.equals("理貨中") ? "selected" : "" %>>理貨中</option>
								<option value="已出貨" <%=status.equals("已出貨") ? "selected" : "" %>>已出貨</option>
								<option value="已完成" <%=status.equals("已完成") ? "selected" : "" %>>已完成</option>
								<option value="已取消" <%=status.equals("已取消") ? "selected" : "" %>>已取消</option>
							</select>
						</td>					
					</tr>
					<tr>
						<td colspan="2" style="text-align:center">
							<input type="button" id="search" value="查詢" >
							<input type="button" id="reset" value="清除" >
							<input type="button" id="add" value="新增" >
						</td>
					</tr>
				</table><!--queryForm !-->
			</form>
		</div>
	</div><!--panel-->
	
	<form id="queryForm" action="#" method="post">
		<input type="hidden" id="id" name="id"> 
		<input id="action" name="action" type="hidden">
		<input id="pageNum" name="pageNum" type="hidden" value="<%=pageNum%>">
		<table class="qTable">
			<tr>
				<th>訂單編號</th>
				<th>訂購人</th>
				<th>狀態</th>
				<th>總金額</th>
				<th>訂購時間</th>
				<th>修改</th>
				<th>取消訂購</th>
			</tr>
			<c:forEach items="${poList }" var="po" varStatus="vs">
				<c:if test="${vs.index >= pageRow && vs.index <= pageRow + 10}" >
					<tr style='background-color:${vs.index % 2 == 0 ? "#f6c184" : "#f9dbb8" }' >
						<td><a href="PODetail.jsp?id=${po.id}">${po.id}</a></td>
						<td>${po.owner}</td>
						<td>${po.status}</td>
						<td>${po.total}</td>
						<td>${po.createTime}</td>
						<c:choose>
							<c:when test="${po.status eq '已取消'}">
								<td></td>
								<td></td>
							</c:when>
							<c:otherwise>
								<td><input type="button" value="修改" name="update" uuid="${po.id}"></td>
								<td><input type="button" value="取消訂購" name="del"	uuid="${po.id}"></td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:if>
			</c:forEach>
			<c:if test="${fn:length(poList) > 10 }">
				<tr>
					<td colspan="6">
						<br>
						<c:if test="${pageRow > 0 }">
						<input type="button" id="pre" name="pre" value="上一頁">
						</c:if>
						<c:if test="${fn:length(poList) > pageRow + 10 }">
						<input type="button" id="next" name="next" value="下一頁">
						</c:if>
						<span>&nbsp;&nbsp;&nbsp;&nbsp;第<%=pageNum+1 %>頁</span>
					</td>
				</tr>
			</c:if>
		</table>
	</form>
</div>

<jsp:include page="../common/footer.jsp"></jsp:include>
