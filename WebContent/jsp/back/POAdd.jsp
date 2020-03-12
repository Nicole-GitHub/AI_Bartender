<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.*"
	import="util.CommonUtil,util.DateUtil"
	import="dao.*"
	import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp"></jsp:include>
<link rel="stylesheet" href="../../css/back/PO.css" />
<script src="../../js/back/POAdd.js"></script>
<%
	PODao dao = new PODao();
	WineDao wineDao = new WineDao();
	CommonUtil comm = new CommonUtil();
	String id = comm.getString(request.getParameter("id"));
	int pageNum = comm.StringToInt(request.getParameter("pageNum"));
	if(pageNum < 0) pageNum = 0;
	String action = id.equals("") ? "新增" : "修改";
	
	ArrayList<PO> poList = new ArrayList<PO>();
	if(id.equals("")){
		id = dao.getId();
	}else{
		PO po = new PO();
		po.setId(id);
		poList = dao.query(po);
	}
	//System.out.println(pageNum);
%>
<c:set var="action" value="<%=action %>" />
<c:set var="id" value="<%=id %>" />
<c:set var="poList" value="<%=poList %>" />
<div class="content">
	<form id="form" action="../../POServlet" method="post">
	<input type="hidden" id="action" name="action" value="${action eq '新增' ? 'add' : 'update'}"/>
		<div>
			<div class="pageName">訂單${action }</div>
			<div class="search">
				<table class="titleTable">
					<tr>
						<td>
							<label>訂單編號：${empty poList[0].id ? id : poList[0].id}</label>
							<input type="hidden" id="id" name="id" value="${empty poList[0].id ? id : poList[0].id}"/>
						</td>
					</tr>
						
					<tr>
						<td>
							<label>訂單狀態：</label>
							<input type="hidden" id="oldStatus" name="oldStatus" value="${poList[0].status}">
							<select id="status" name="status">
								<option value="">=== 請選擇 ===</option>
								<option value="等待專員聯繫" ${poList[0].status eq '等待專員聯繫' ? 'selected' : '' }>等待專員聯繫</option>
								<option value="訂購成功" ${poList[0].status eq '訂購成功' ? 'selected' : '' }>訂購成功</option>
								<option value="撿貨中" ${poList[0].status eq '撿貨中' ? 'selected' : '' }>撿貨中</option>
								<option value="理貨中" ${poList[0].status eq '理貨中' ? 'selected' : '' }>理貨中</option>
								<option value="已出貨" ${poList[0].status eq '已出貨' ? 'selected' : '' }>已出貨</option>
								<option value="已完成" ${poList[0].status eq '已完成' ? 'selected' : '' }>已完成</option>
								<option value="已取消" ${poList[0].status eq '已取消' ? 'selected' : '' }>已取消</option>
							</select>
						</td>
						<td>
							<label>訂單總金額：<span>${empty poList[0].total ? 0 : poList[0].total}</span></label>
							<input type="hidden" id="total" name="total" value="${empty poList[0].total ? 0 : poList[0].total}"/>
						</td>
					</tr>
					<tr>
						<td>
							<label>物流編號：</label>
							<input type="text" id="freightId" name="freightId" value="${empty poList[0].freightId ? '' : poList[0].freightId}">
						</td>
						<td>
							<label>貨運商名稱：</label>
							<input type="text" id="freightName" name="freightName" value="${empty poList[0].freightName ? '' : poList[0].freightName}">
						</td>
					</tr>
					
					<tr>
						<td>
							<label>訂購人：</label>
							<c:choose >
							 <c:when test="${empty poList[0].owner}">
								<select id="owner" name="owner">
									<option value="">=== 請選擇 ===</option>
									<c:forEach items="<%=new UsersDao().getUsersList() %>" var="user">
										<option value="${user.email}" ${poList[0].owner eq user.email ? "selected" : ""}>${user.name} (${user.email})</option>
									</c:forEach>
								</select>
							 </c:when>
							 <c:otherwise>
							 	<input type="hidden" id="owner" name="owner" value="${poList[0].owner}" >${poList[0].owner}
							 </c:otherwise>
							</c:choose>
						</td>
						<td>
							<label>${action }人員： admin</label>
							<input type="hidden" id="${action eq '新增' ? 'createUser' : 'updateUser' }" name="${action eq '新增' ? 'createUser' : 'updateUser' }" value="admin">
						</td>
					</tr>
				</table><!--queryForm !-->
			</div>
		</div><!--panel-->
		<p/>
		<div class="queryTitle">訂單明細&emsp;<input type="button" value="新增訂單明細" name="addDetail" id="addDetail"></div>
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
							<c:forEach items="<%=wineDao.getWineList() %>" var="wine">
								<option value="${wine.id}" price="${wine.price}">${wine.id} ${wine.chName}</option>
							</c:forEach>
						</select>
					</td>
					<td><input type="hidden" name="price" value="0"><span>0</span></td>
					<td><input type="number" name="quantity" value="0" min="0" max="999"></td>
					<td><input type="hidden" name="unit" value="瓶"><span>瓶</span></td>
					<td><input type="hidden" name="subtotal" value="0"><span>0</span></td>
					<td><input type="button" value="刪除" name="delete"></td>
	    		</tr>
	    		<c:choose>
					<c:when test="${empty poList[0].id}">
						<tr>
							<td>
								<select name="wineId">
									<option value="">請選擇</option>
									<c:forEach items="<%=wineDao.getWineList() %>" var="wine">
										<option value="${wine.id}" price="${wine.price}">${wine.id} ${wine.chName }</option>
									</c:forEach>
								</select>
							</td>
							<td><input type="hidden" name="price" value="0"><span>0</span></td>
							<td><input type="number" name="quantity" value="0" min="0" max="999"></td>
							<td><input type="hidden" name="unit" value="瓶"><span>瓶</span></td>
							<td><input type="hidden" name="subtotal" value="0" ><span>0</span></td>
							<td><input type="button" value="刪除" name="delete"></td>
						</tr>
					</c:when>
					<c:otherwise>
			    		<c:forEach items="${poList[0].poDetail}" var="detail">
							<tr>
								<td>
									<select name="wineId">
										<option value="">請選擇</option>
										<c:forEach items="<%=wineDao.getWineList() %>" var="wine">
											<option value="${wine.id}" price="${wine.price}" ${detail.wineId eq wine.id ? "selected" : "" }>${wine.id} ${wine.chName }</option>
										</c:forEach>
									</select>
								</td>
								<td><input type="hidden" name="price" value="${detail.price }"><span>${detail.price }</span></td>
								<td><input type="number" name="quantity" value="${detail.quantity }" min="0" max="999"></td>
								<td><input type="hidden" name="unit" value="${detail.unit }"><span>${detail.unit }</span></td>
								<td><input type="hidden" name="subtotal" value="${detail.subtotal }"><span>${detail.subtotal }</span></td>
								<td><input type="button" value="刪除" name="delete"></td>
							</tr>
			    		</c:forEach>
					</c:otherwise>
	    		</c:choose>
			</table>
		</div>
		
		<div class="footerButton">
			<input type="button" value="確定${action }" name="add" id="add">
			<input type="button" value="取消" name="cancel" id="cancel" >
		</div>
	</form>
</div>

<jsp:include page="footer.jsp"></jsp:include>
