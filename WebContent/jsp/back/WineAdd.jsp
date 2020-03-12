<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.*"
	import="util.CommonUtil,util.DateUtil"
	import="dao.*"
	import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp"/>
<link rel="stylesheet" href="../../css/back/Wine.css" />
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

<style>
.upload_cover {
position: relative;
/* width: 100px;
height: 100px; */
/* text-align: center; */
cursor: pointer;
/* background: #efefef;
border: 1px solid #595656; */
}
#upload_input {
display: none;
}
.upload_icon {
font-weight: bold;
font-size: 180%;
position: absolute;
left: -18px;
    top: -9px;
/* left: 0;
width: 100%;
top: 20%; */
border:1px black solid;
}
.delAvatar {
position: absolute;
right: 2px;
top: 2px;
}
</style>
<div class="content">
		<div>
			<div class="pageName">商品${action }</div>
			<div class="search">
				<table class="titleTable">
					<tr>
						<td style="height:50px">
							<form id="uploadForm" action="../../uploadImg" enctype="multipart/form-data" method="post">
								<label class="upload_cover">
									<input type="file" name="uploadImg" id="upload_input">
									<span class="upload_icon">➕</span>
									<i class="delAvatar fa fa-times-circle-o" title="刪除"></i>
								</label>
							</form>
						</td>
						<td>
							<label>商品編號：<span>${empty wineList[0].id ? '' : wineList[0].id}</span></label>
							<input type="hidden" id="id" name="id" value="${empty wineList[0].id ? id : wineList[0].id}"/>
						</td>
						<!-- <form id="uploadForm" action="../../Import" enctype="multipart/form-data" method="post">  -->
							<!-- <input type="file" name="uploadXlsx" id="uploadXlsx"> -->
						<!-- </form> -->
					</tr>
				</table>
			<!-- <form id="form" action="../../WineServlet" method="post"> -->
				<table>
					<tr>
						<td>
							<label>商品狀態：</label>
							<input type="hidden" id="oldStatus" name="oldStatus" value="${wineList[0].status}">
							<select id="status" name="status">
								<option value="">=== 請選擇 ===</option>
								<option value="等待專員聯繫" ${wineList[0].status eq '等待專員聯繫' ? 'selected' : '' }>等待專員聯繫</option>
								<option value="訂購成功" ${wineList[0].status eq '訂購成功' ? 'selected' : '' }>訂購成功</option>
								<option value="撿貨中" ${wineList[0].status eq '撿貨中' ? 'selected' : '' }>撿貨中</option>
								<option value="理貨中" ${wineList[0].status eq '理貨中' ? 'selected' : '' }>理貨中</option>
								<option value="已出貨" ${wineList[0].status eq '已出貨' ? 'selected' : '' }>已出貨</option>
								<option value="已完成" ${wineList[0].status eq '已完成' ? 'selected' : '' }>已完成</option>
								<option value="已取消" ${wineList[0].status eq '已取消' ? 'selected' : '' }>已取消</option>
							</select>
						</td>
						<td>
							<label>商品總金額：<span>${empty wineList[0].total ? 0 : wineList[0].total}</span></label>
							<input type="hidden" id="total" name="total" value="${empty wineList[0].total ? 0 : wineList[0].total}"/>
						</td>
					</tr>
					<tr>
						<td>
							<label>物流編號：</label>
							<input type="text" id="freightId" name="freightId" value="${empty wineList[0].freightId ? '' : wineList[0].freightId}">
						</td>
						<td>
							<label>貨運商名稱：</label>
							<input type="text" id="freightName" name="freightName" value="${empty wineList[0].freightName ? '' : wineList[0].freightName}">
						</td>
					</tr>
					
					<tr>
						<td>
							<label>訂購人：</label>
							<c:choose >
							 <c:when test="${empty wineList[0].owner}">
								<select id="owner" name="owner">
									<option value="">=== 請選擇 ===</option>
									<c:forEach items="<%=new UsersDao().getUsersList() %>" var="user">
										<option value="${user.email}" ${wineList[0].owner eq user.email ? "selected" : ""}>${user.name} (${user.email})</option>
									</c:forEach>
								</select>
							 </c:when>
							 <c:otherwise>
							 	<input type="hidden" id="owner" name="owner" value="${wineList[0].owner}" >${wineList[0].owner}
							 </c:otherwise>
							</c:choose>
						</td>
						<td>
							<label>${action }人員： admin</label>
							<input type="hidden" id="${action eq '新增' ? 'createUser' : 'updateUser' }" name="${action eq '新增' ? 'createUser' : 'updateUser' }" value="admin">
						</td>
					</tr>
				</table><!--queryForm !-->
			<!-- </form> -->
			</div>
		</div><!--panel-->
		
		<div class="footerButton">
			<input type="hidden" id="action" name="action" value="${action eq '新增' ? 'add' : 'update'}"/>
			<input type="button" value="確定${action }" name="add" id="add">
			<input type="button" value="取消" name="cancel" id="cancel" >
		</div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
