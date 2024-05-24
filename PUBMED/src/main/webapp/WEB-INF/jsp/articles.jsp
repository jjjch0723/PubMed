<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Article Management</title>
<style type="text/css">
*{
	style="margin-left: 10px;"
}
th {
	text-align: right;
}

td {
	text-align: right;
}
</style>
</head>
<body>
	<h1>Article Management</h1>
	<table >
		<tr>
			<th>PMID list crawling with Abbreviation</th>
			<td>
				<button onclick="location.href='/abbs-pmid'">Crawling</button>
			</td>
		</tr>
		<tr>
			<th>Insert Crawled PMID list</th>
			<td>
				<button onclick="location.href='/insertpmid'">INSERT</button>
			</td>
		</tr>
		<tr>
			<th>Abstract crawling with PMID</th>
			<td>
				<button onclick="location.href='/pmid-abstract'">Crawling</button>
			</td>
		</tr>
		<tr>
			<th>Insert Crawled Abstract</th>
			<td>
				<button onclick="location.href='/insertabstract'">INSERT</button>
			</td>
		</tr>
		<tr>
			<th>Journal Management</th>
			<td><input type="button" value="Journal"
				onclick="location.href='/journal'" /></td>
		</tr>
	</table>
</body>
</html>