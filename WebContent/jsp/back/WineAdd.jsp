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
	int pageNum = comm.StringToInt(request.getParameter("pageNum"));
	if(pageNum < 0) pageNum = 0;
	String action = id.equals("") ? "新增" : "修改";
	
	ArrayList<Wine> wineList = new ArrayList<Wine>();
	if(!id.equals("")){
		Wine wine = new Wine();
		wine.setId(id);
		wineList = dao.query(wine);
	}
	//System.out.println(pageNum);
%>
<c:set var="action" value="<%=action %>" />
<c:set var="id" value="<%=id %>" />
<c:set var="wineList" value="<%=wineList %>" />

<div class="content">
	<div>
		<div class="pageName">商品${action }</div>
		<div class="addContent">
			<form id="form" action="../../WineServlet" enctype="multipart/form-data" method="post">
				<table class="contentTable">
					
					<tr>
						<td rowspan=10>
							<div><img src="../../${wineList[0].imgPath }" width="300px"></div>
							<span style="font-weight: bold;color: red;">上傳商品圖片</span><input type="file" name="imgPath" id="imgPath">
						</td>
					</tr>
					<tr>
						<th><label>商品編號：</label></th>
						<td colspan="3">
							<span>${empty wineList[0].id ? '' : wineList[0].id}</span>
							<input type="hidden" id="id" name="id" value="${empty wineList[0].id ? id : wineList[0].id}"/>
						</td>
					</tr>
					<tr>
						<th><label>英文名稱：</label></th>
						<td colspan="3">
							<input type="text" id="enName" name="enName" size="60" value="${empty wineList[0].enName ? '' : wineList[0].enName}">
						</td>
					</tr>
					<tr>
						<th><label>中文名稱：</label></th>
						<td colspan="3">
							<input type="text" id="chName" name="chName" size="60" value="${empty wineList[0].chName ? '' : wineList[0].chName}">
						</td>
					</tr>
					<tr>
						<th><label>品種：</label></th>
						<td colspan="3">
							<input type="text" id="grape" name="grape" size="40" value="${empty wineList[0].grape ? '' : wineList[0].grape}">
						</td>
					</tr>
					<tr>
						<th><label>酒種：</label></th>
						<td>
							<select id="type" name="type">
								<option value="">=== 請選擇 ===</option>
								<option value="紅酒" ${wineList[0].type eq '紅酒' ? 'selected' : '' }>紅酒</option>
								<option value="白酒" ${wineList[0].type eq '白酒' ? 'selected' : '' }>白酒</option>
							</select>
						</td>
						<th><label>產地：</label></th>
						<td>
							<select id="place" name="place">
								<option value="">=== 請選擇 ===</option>
								<option value="Australia" ${wineList[0].place eq 'Australia' ? 'selected' : '' }>Australia</option>
								<option value="Chlie" ${wineList[0].place eq 'Chlie' ? 'selected' : '' }>Chlie</option>
								<option value="France" ${wineList[0].place eq 'France' ? 'selected' : '' }>France</option>
								<option value="USA" ${wineList[0].place eq 'USA' ? 'selected' : '' }>USA</option>
							</select>
						</td>
					</tr>
					<tr>
						<th><label>酒精濃度：</label></th>
						<td>
							<input type="number" id="percent" name="percent" max=100 min=0 value="${empty wineList[0].percent ? 0 : wineList[0].percent}">
						</td>
						<th><label>容量：</label></th>
						<td>
							<input type="number" id="ml" name="ml" max=1000 min=0 value="${empty wineList[0].ml ? 0 : wineList[0].ml}">
						</td>
					</tr>
					<tr>
						<th><label>銷售單位：</label></th>
						<td>
							<input type="text" id="unit" name="unit" size="5" value="${empty wineList[0].unit ? '' : wineList[0].unit}">
						</td>
						<th><label>價格：</label></th>
						<td>
							<input type="number" id="price" name="price" max=999999999 min=0 value="${empty wineList[0].price ? 0 : wineList[0].price}">
						</td>
					</tr>
					<tr>
						<th><label>商品狀態：</label></th>
						<td colspan="3">
							<select id="status" name="status">
								<option value="">=== 請選擇 ===</option>
								<option value="已上架" ${wineList[0].status eq '已上架' ? 'selected' : '' }>已上架</option>
								<option value="已下架" ${wineList[0].status eq '已下架' ? 'selected' : '' }>已下架</option>
								<option value="完售" ${wineList[0].status eq '完售' ? 'selected' : '' }>完售</option>
								<option value="缺貨" ${wineList[0].status eq '缺貨' ? 'selected' : '' }>缺貨</option>
							</select>
						</td>
					</tr>
					<tr>
						<th><label>特色：</label></th>
						<td colspan="3">
							<textarea rows="5" cols="60" id="feature" name="feature">${empty wineList[0].feature ? '' : wineList[0].feature}</textarea>
						</td>
					</tr>
				</table>
				<div class="footerButton">
					<input type="hidden" id="action" name="action" value="${action eq '新增' ? 'add' : 'update'}"/>
					<input type="button" value="確定${action }" name="add" id="add">
					<input type="button" value="取消" name="cancel" id="cancel" >
				</div>
			</form>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
