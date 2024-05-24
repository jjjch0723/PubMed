<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
*{
	style="margin-left: 10px;"
}
th {
	text-align: left;
}

td {
	text-align: left;
}
</style>
<title>Journal Management</title>
</head>
<body>
	<h1>Journal Management</h1>
	
	<!-- 저널의 총 개수를 표시합니다 -->

	<c:set var="maxOffset" value="${fn:substringBefore(count / 500, '.')}" />

	<table style="margin-left: 10px;">
	<!-- 
		<tr>
			<td colspan="2"> 저널 총 개수: ${count} </td>
		</tr>
		<form action="${pageContext.request.contextPath}/outline" method="get">
			<tr>
				<th>
				 <label for="offset">오프셋 입력 (0 ~ ${maxOffset}):</label>
				</th>
				<td> 
					<input type="number" id="offset" name="offset" min="0" max="${maxOffset}" required>
				</td>
			</tr>
			<tr>
				<th>Outline Crawling</th>
				<td><input type="submit" value="Crawling"></td>
			</tr>
		</form>
		 -->
		<tr>
				<th>Outline Crawling</th>
				<td><input type="button" value="Crawling"
				onclick="location.href='/outline'" /></td>
		</tr>
		<tr>
			<th>Insert Crawled Journal Data</th>
			<td><input type="button" value="INSERT"
				onclick="location.href='/insertoutline'" /></td>
		</tr>
		<tr>
			<th>Article Management</th>
			<td><input type="button" value="Article"
				onclick="location.href='/article'" /></td>
		</tr>
	</table>
</body>
</html>