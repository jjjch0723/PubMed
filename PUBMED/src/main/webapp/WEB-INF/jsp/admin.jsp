<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>Main</title>
</head>
<body>
	<table border="0">
		<tr>
			<form action="/upload" method="post" enctype="multipart/form-data">
				<th>
					<input type="file" name="file" id="file">
				</th>
				<td>
					<input type="submit" value="Upload File">
				</td>
			</form>
		</tr>
		<tr>
			<th>Journal Management</th>
			<td>
				<input type="button" value="Journal" onclick="location.href='/journal'" />
			</td>
		</tr>
		<tr>
			<th>Article Management</th>
			<td>
				<input type="button" value="Article" onclick="location.href='/article'" />
			</td>
		</tr>
	</table>
	
</body>
</html>