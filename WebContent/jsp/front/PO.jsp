<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../../css/PO.css">
<title>Insert title here</title>
</head>
<body>
	<div style="width: 80%; margin: auto">
		<div>
			<h1>
				<b>訂單查詢</b>
			</h1>
		</div>
		<c:forEach begin="1" end="3" step="1">
			<div>訂購日期: 2020/01/31</div>
			<table class="qTable" style="border:1px black solid">
				<tr style="background-color: #f3b355">
					<td>訂單編號</td>
					<td>訂單時間</td>
					<td>狀態</td>
					<td>總金額</td>
					<td>取消</td>
				</tr>
				<tr>
					<td>O001</td>
					<td>10:30:00</td>
					<td>待處理</td>
					<td>300</td>
					<td><input type="button" name="cancel" value="取消訂單"></td>
				</tr>
				<tr>
					<td colspan="5">
						<table class="qTable" style="width: 95%; margin: 1%; border:1px black solid">
							<tr style="background-color: #f3b355">
								<td>商品名稱</td>
								<td>單價</td>
								<td></td>
								<td>數量</td>
								<td></td>
								<td>小記</td>
							</tr>
							<c:forEach begin="1" end="3" step="1">
								<tr>
									<td>Vina Quebrada de Macul Domus Aurea Cabernet Sauvignon,
										Maipo Valley, Chile</td>
									<td>100</td>
									<td>X</td>
									<td>3</td>
									<td>=</td>
									<td>300</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
			<br>
		</c:forEach>
	</div>
</body>
</html>