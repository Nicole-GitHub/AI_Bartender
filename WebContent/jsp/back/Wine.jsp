<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="model.Wine,util.CommonUtil,dao.WineDao,dao.UsersDao,java.util.ArrayList" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<% 
	request.setCharacterEncoding("UTF-8");

	CommonUtil comm = new CommonUtil();
	Wine wine = new Wine();
	WineDao dao = new WineDao();
	int pageNum = comm.StringToInt(request.getParameter("pageNum"));
	if(pageNum < 0) pageNum = 0;
	String status = comm.getString(request.getParameter("status"));
	String chName = comm.getString(request.getParameter("chName"));
	String enName = comm.getString(request.getParameter("enName"));
	String place = comm.getString(request.getParameter("place"));
	String type = comm.getString(request.getParameter("type"));
	String grape = comm.getString(request.getParameter("grape"));
	String ImportRS = comm.getString(request.getParameter("ImportRS"));
	
	wine.setStatus(status);
	wine.setPlace(place);
	wine.setType(type);
	wine.setGrape(grape);
	wine.setChName(chName);
	wine.setEnName(enName);
	/* System.out.println("status="+status);
	System.out.println("place="+place);
	System.out.println("type="+type);
	System.out.println("grape="+grape);
	System.out.println("chName="+chName);
	System.out.println("enName="+enName); */
%>

<c:set var="pageRow" value="<%=pageNum * 10  %>" />
<c:set var="wineList" value="<%=dao.query(wine)  %>" />
<jsp:include page="header.jsp"/>
<link rel="stylesheet" href="../../css/back/PO.css" />
<link rel="stylesheet" href="../../css/back/Wine_b3.css" />
<script src="../../js/back/Wine.js"></script>
<script type="text/javascript">
<% if(ImportRS.equals("ok")){%>
	alert("商品匯入成功");
	location.href="Wine.jsp";
<%}else if(ImportRS.equals("fail")){%>
	alert("商品匯入成功");
	location.href="Wine.jsp";
<%}%>
</script>
	<div class="content">
	<table><tr><td>
		
			<div class="search">
				<form id="searchForm" action="Wine.jsp" method="post">
					<table class="titleTable">
						<tr>
							<td>
								<label>英文名稱：</label>
								<input type="text" id="enName" name="enName" value="<%=enName%>">
							</td>
							<td>
								<label>中文名稱：</label>
								<input type="text" id="chName" name="chName" value="<%=chName%>">
							</td>
						</tr>
						<tr>
							<td>
								<label>產地：</label>
								<select id="place" name="place">
									<option value="">=== 請選擇 ===</option>
									<option value="Australia" <%=place.equals("Australia") ? "selected" : "" %>>Australia</option>
									<option value="Chlie" <%=place.equals("Chlie") ? "selected" : "" %>>Chlie</option>
									<option value="France" <%=place.equals("France") ? "selected" : "" %>>France</option>
									<option value="USA" <%=place.equals("USA") ? "selected" : "" %>>USA</option>
								</select>
							</td>
							<td>
								<label>品種：</label>
								<input type="text" id="grape" name="grape" value="<%=grape%>">
							</td>
						</tr>
						<tr>
							<td>
								<label>酒種：</label>
								<select id="type" name="type">
									<option value="">=== 請選擇 ===</option>
									<option value="白酒" <%=type.equals("白酒") ? "selected" : "" %>>白酒</option>
									<option value="紅酒" <%=type.equals("紅酒") ? "selected" : "" %>>紅酒</option>
								</select>
							</td>
							<td>
								<label>訂單狀態：</label>
								<select id="status" name="status">
									<option value="">=== 請選擇 ===</option>
									<option value="已上架" <%=status.equals("已上架") ? "selected" : "" %>>已上架</option>
									<option value="已下架" <%=status.equals("已下架") ? "selected" : "" %>>已下架</option>
									<option value="完售" <%=status.equals("完售") ? "selected" : "" %>>完售</option>
									<option value="缺貨" <%=status.equals("缺貨") ? "selected" : "" %>>缺貨</option>
								</select>
							</td>					
						</tr>
						<tr>
							<td>
							</td>
							<td style="padding:10px 0 0 20px">
								<input type="button" id="search" value="查詢" >
								<input type="button" id="reset" value="清除" >
								<input type="button" id="add" value="新增" >
							</td>
						</tr>
					</table><!--queryForm !-->
				</form>
			</div>
		</td><td>
			<div class="pageNameAndFile">
				<div class="pageName">商品查詢</div>
				<div class="ExcelAction">
					<form id="uploadForm" action="../../Import" enctype="multipart/form-data" method="post"> 
						<span>匯入商品資訊檔</span><input type="file" name="uploadXlsx" id="uploadXlsx">
					</form>
					<input type="button" id="export" name="export" value="匯出至Excel">
					<input type="button" id="getSample" name="getSample" value="下載匯入商品資訊範例檔">
				</div>
			</div>
		</td></tr>
	</table>
		<!--panel-->
		<form id="queryForm" action="#" method="post">
			<input type="hidden" id="id" name="id"> 
			<input id="action" name="action" type="hidden">
			<input id="pageNum" name="pageNum" type="hidden" value="<%=pageNum%>">
			<table class="qTable">
				<tr class="titleTR">
					<th style="width: 7%;">圖片</th>
					<th style="width: 16%;">商品資訊</th>
					<th style="width: 10%;">商品規格</th>
					<th style="width: 15%;">品種</th>
					<th style="width: 37%;">商品特色</th>
					<th style="width: 12%;">建立資訊</th>
					<th style="width: 3%;"></th>
				</tr>
				<c:forEach items="${wineList }" var="wine" varStatus="vs">
					<c:if test="${vs.index >= pageRow && vs.index < pageRow + 10}" >
						<tr style='background-color:${vs.index % 2 == 0 ? "#FFF" : "#FDEBE0" }' >
							<td>
								<a href="WineDetail.jsp?id=${wine.id}">
									<img src="../../${wine.imgPath}" style="height:8vh;border:1px #898989a6 solid;" >
								</a>
							</td>
							<td>
								<table>
									<tr>
										<td style="color:#648875">${wine.enName}<br><br></td>
									</tr>
									<tr>
										<td style="color:#A56433">${wine.chName}<br><br></td>
									</tr>
									<tr>
										<td><b>商品編號:</b> ${wine.id}</td>
									</tr>
									<tr>
										<td><b>商品狀態:</b> ${wine.status}</td>
									</tr>
								</table>
							</td>
							<td>
								<table>
									<tr><td><b>產地:</b> ${wine.place}</td></tr>
									<tr><td><b>酒種:</b> ${wine.type}</td></tr>
									<tr><td><b>價格:</b> ${wine.price}</td></tr>
									<tr><td><b>濃度:</b> ${wine.percent}</td></tr>
									<tr><td><b>容量:</b> ${wine.ml}</td></tr>
									<tr><td><b>單位:</b> ${wine.unit}</td></tr>
								</table>
							</td>
							<td>${wine.grape}</td>
							<td>${wine.feature}</td>
							<td>
								<table>
									<tr><td><b>建立人:</b><br>${wine.createUser}</td></tr>
									<tr><td><b>建立時間:</b><br>${wine.createTime}</td></tr>
									<tr><td><b>最後修改人:</b><br>${wine.updateUser}</td></tr>
									<tr><td><b>最後修改時間:</b><br>${wine.updateTime}</td></tr>
								</table>
							</td>
							<td>
								<input type="button" value="修改" name="update" uuid="${wine.id}"><br>
								<input type="button" value="刪除" name="del"	uuid="${wine.id}">
							</td>
						</tr>
					</c:if>
				</c:forEach>
				<c:if test="${empty wineList }">
					<tr><td colspan="7">查無資料</td></tr>
				</c:if>
			</table>
			<c:if test="${not empty wineList}">
				<div class="page">
					<div><input type="button" id="pre" name="pre" ${pageRow > 0 ? '' : 'disabled'} value="<"></div>
					<div class="showPageNum"><span id="page">&nbsp; <%=pageNum+1 %> &nbsp;</span></div>
					<div><input type="button" id="next" name="next" ${fn:length(wineList) > pageRow + 10 ? '' : 'disabled'} value=">"></div>
				</div>
			</c:if>
		</form>
	</div>
<jsp:include page="footer.jsp"/>


