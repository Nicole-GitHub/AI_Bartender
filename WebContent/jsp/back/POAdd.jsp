<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.*"
	import="util.CommonUtil,util.DateUtil"
	import="dao.PODao"
	import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../common/header.jsp"></jsp:include>
<link rel="stylesheet" href="../../css/PO.css" />
<script src="../../js/POAdd.js"></script>
<%
	PODao dao = new PODao();
	CommonUtil comm = new CommonUtil();
	String id = comm.getString(request.getParameter("id"));
	int pageNum = comm.StringToInt(request.getParameter("pageNum"));
	if(pageNum < 0) pageNum = 0;
	id = id.equals("") ? dao.getId() : id;
	String now = new DateUtil().getNowDateTimeFormat("yyyy-MM-dd HH:mm:ss");
	//System.out.println(pageNum);
%>
<div class="body">
	<form id="form" action="../../POServlet" method="post">
	<input type="hidden" id="action" name="action" value="add"/>
		<div>
			<div><h1><b>訂單新增</b></h1></div>
			<div>
				<table class="titleTable">
					<tr>
						<td>
							<label>訂單編號：<%=id %></label>
							<input type="hidden" id="id" name="id" value="<%=id %>"/>
						</td>
						
						<td>
							<label>訂單總金額：<span>0</span></label>
							<input type="hidden" id="total" name="total" value="0"/>
						</td>
					</tr>
					<tr>
						<td>
							<label>訂購人：</label>
							<select id="owner" name="owner">
								<option value="">=== 請選擇 ===</option>
								<c:forEach items="<%=dao.getUsersList() %>" var="user">
									<option value="${user.email}">${user.email}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<label>建立人員： admin</label>
							<input type="hidden" id="createUser" name="createUser" value="admin">
						</td>
					</tr>
				</table><!--queryForm !-->
			</div>
		</div><!--panel-->
		<p/>
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
			    <tr id="tempRow" style="display: none;">
			    	<td>
						<select name="wineId">
							<option value="">請選擇</option>
							<c:forEach items="<%=dao.getWineList() %>" var="wine">
								<option value="${wine.id}" price="${wine.price}">${wine.id} ${wine.chName}</option>
							</c:forEach>
						</select>
					</td>
					<td><input type="hidden" name="price" value="0"><span>0</span></td>
					<td><input type="number" name="quantity" value="0" min="0" max="999"></td>
					<td><input type="hidden" name="unit" value="瓶"><span>瓶</span></td>
					<td><input type="hidden" name="subtotal" ><span>0</span></td>
					<td><input type="button" value="刪除" name="delete"></td>
	    		</tr>
				<tr>
					<td>
						<select name="wineId">
							<option value="">請選擇</option>
							<c:forEach items="<%=dao.getWineList() %>" var="wine">
								<option value="${wine.id}" price="${wine.price}">${wine.id} ${wine.chName }</option>
							</c:forEach>
						</select>
					</td>
					<td><input type="hidden" name="price" value="0"><span>0</span></td>
					<td><input type="number" name="quantity" value="0" min="0" max="999"></td>
					<td><input type="hidden" name="unit" value="瓶"><span>瓶</span></td>
					<td><input type="hidden" name="subtotal" ><span>0</span></td>
					<td><input type="button" value="刪除" name="delete"></td>
				</tr>
			</table>
		</div>
		
		<div>
			<input type="button" value="確定新增" name="add" id="add">
			<input type="button" value="取消" name="cancel" id="cancel" >
		</div>
	</form>
</div>

<jsp:include page="../common/footer.jsp"></jsp:include>
